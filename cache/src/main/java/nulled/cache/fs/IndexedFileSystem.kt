package nulled.cache.fs

import com.google.common.base.Preconditions
import nulled.cache.util.FileSystemConstants
import nulled.cache.index.Index.Companion.decode
import nulled.cache.archive.Archive
import nulled.cache.archive.Archive.Companion.decode
import nulled.cache.index.Index
import nulled.cache.util.FileDescriptor
import java.io.Closeable
import java.io.FileNotFoundException
import java.io.IOException
import java.io.RandomAccessFile
import java.nio.ByteBuffer
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import java.util.zip.CRC32

class IndexedFileSystem(val base: Path, val readOnly: Boolean = false) : Closeable {
    private val cache: HashMap<FileDescriptor, Archive> = HashMap(FileSystemConstants.ARCHIVE_COUNT)
    private val indices = arrayOfNulls<RandomAccessFile>(256)
    private var crcTable: ByteBuffer? = null
    private var crcs: IntArray? = null
    private var data: RandomAccessFile? = null

    init {
        detectLayout(base)
    }

    fun getCrcs(): IntArray {
        if (crcs == null) {
            val buffer = getCrcTable()
            crcs = IntArray((buffer.remaining() / Integer.BYTES) - 1)
            Arrays.setAll(crcs) { crc: Int -> buffer.getInt() }
        }
        return crcs!!
    }

    fun getArchive(type: Int, file: Int): Archive {
        val descriptor = FileDescriptor(type, file)
        var cached = cache[descriptor]

        if (cached == null) {
            cached = decode(getFile(descriptor))

            synchronized(this) {
                cache.put(descriptor, cached)
            }
        }

        return cached
    }

    fun getCrcTable(): ByteBuffer {
        if (readOnly) {
            synchronized(this) {
                if (crcTable != null) {
                    return crcTable!!.duplicate()
                }
            }

            val archives: Int = getFileCount(0)
            var hash = 1234
            val crcs = IntArray(archives)

            val crc32 = CRC32()
            for (i in 1 until crcs.size) {
                crc32.reset()

                val buffer: ByteBuffer = getFile(0, i)
                val bytes = ByteArray(buffer.remaining())
                buffer[bytes, 0, bytes.size]
                crc32.update(bytes, 0, bytes.size)

                crcs[i] = crc32.value.toInt()
            }

            val buffer = ByteBuffer.allocate((crcs.size + 1) * Integer.BYTES)
            for (crc in crcs) {
                hash = (hash shl 1) + crc
                buffer.putInt(crc)
            }

            buffer.putInt(hash)
            buffer.flip()

            synchronized(this) {
                crcTable = buffer.asReadOnlyBuffer()
                return crcTable!!.duplicate()
            }
        }

        throw IllegalStateException("Cannot get CRC table from a writable file system.")
    }

    private fun getFileCount(type: Int): Int {
        Preconditions.checkElementIndex(type, indices.size, "File type out of bounds.")

        val indexFile = indices[type]!!
        synchronized(indexFile) {
            return (indexFile.length() / FileSystemConstants.INDEX_SIZE).toInt()
        }
    }

    private fun getIndex(descriptor: FileDescriptor): Index {
        val index = descriptor.type
        Preconditions.checkElementIndex(index, indices.size, "File descriptor type out of bounds.")

        val buffer = ByteArray(FileSystemConstants.INDEX_SIZE)
        val indexFile = indices[index]!!
        synchronized(indexFile) {
            val position = (descriptor.file * FileSystemConstants.INDEX_SIZE).toLong()
            if (position >= 0 && indexFile.length() >= position + FileSystemConstants.INDEX_SIZE) {
                indexFile.seek(position)
                indexFile.readFully(buffer)
            } else {
                throw FileNotFoundException("Could not find find index.")
            }
        }

        return decode(buffer)
    }

    fun getFile(type: Int, file: Int): ByteBuffer {
        return getFile(FileDescriptor(type, file))
    }

    fun getFile(descriptor: FileDescriptor): ByteBuffer {
        val index: Index = getIndex(descriptor)
        val buffer = ByteBuffer.allocate(index.size)

        var position = (index.block * FileSystemConstants.BLOCK_SIZE).toLong()
        var read = 0
        val size = index.size
        var blocks = size / FileSystemConstants.CHUNK_SIZE
        if (size % FileSystemConstants.CHUNK_SIZE != 0) {
            blocks++
        }

        for (i in 0 until blocks) {
            val header = ByteArray(FileSystemConstants.HEADER_SIZE)
            synchronized(data!!) {
                data!!.seek(position)
                data!!.readFully(header)
            }

            position += FileSystemConstants.HEADER_SIZE.toLong()

            val nextFile = (header[0].toInt() and 0xFF) shl 8 or (header[1].toInt() and 0xFF)
            val curChunk = (header[2].toInt() and 0xFF) shl 8 or (header[3].toInt() and 0xFF)
            val nextBlock =
                (header[4].toInt() and 0xFF) shl 16 or ((header[5].toInt() and 0xFF) shl 8) or (header[6].toInt() and 0xFF)
            val nextType = header[7].toInt() and 0xFF

            Preconditions.checkArgument(i == curChunk, "Chunk id mismatch.")

            var chunkSize = size - read
            if (chunkSize > FileSystemConstants.CHUNK_SIZE) {
                chunkSize = FileSystemConstants.CHUNK_SIZE
            }

            val chunk = ByteArray(chunkSize)
            synchronized(data!!) {
                data!!.seek(position)
                data!!.readFully(chunk)
            }
            buffer.put(chunk)

            read += chunkSize
            position = nextBlock.toLong() * FileSystemConstants.BLOCK_SIZE.toLong()

            // if we still have more data to read, check the validity of the header
            if (size > read) {
                if (nextType != descriptor.type + 1) {
                    throw IOException("File type mismatch.")
                }

                if (nextFile != descriptor.file) {
                    throw IOException("File id mismatch.")
                }
            }
        }

        buffer.flip()
        return buffer
    }

    private fun detectLayout(base: Path) {
        var indexCount = 0
        for (index in indices.indices) {
            val idx = base.resolve("main_file_cache.idx$index")
            if (Files.exists(idx) && !Files.isDirectory(idx)) {
                indexCount++
                indices[index] = RandomAccessFile(idx.toFile(), if (readOnly) "r" else "rw")
            }
        }
        if (indexCount <= 0) {
            throw FileNotFoundException("No index file(s) present in $base.")
        }

        val resources = base.resolve("main_file_cache.dat")
        if (Files.exists(resources) && !Files.isDirectory(resources)) {
            data = RandomAccessFile(resources.toFile(), if (readOnly) "r" else "rw")
        } else {
            throw FileNotFoundException("No data file present.")
        }
    }

    override fun close() {
        if (data != null) {
            synchronized(data!!) {
                data!!.close()
            }
        }

        for (index in indices) {
            if (index != null) {
                synchronized(index) {
                    index.close()
                }
            }
        }
    }
}