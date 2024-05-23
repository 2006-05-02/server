package nulled.cache.archive

import org.apollo.util.BufferUtil
import org.apollo.util.CompressionUtil
import java.io.FileNotFoundException
import java.io.IOException
import java.nio.ByteBuffer
import java.util.*

class Archive(val entries: Array<ArchiveEntry?>) {
    @Throws(FileNotFoundException::class)
    fun getEntry(name: String): ArchiveEntry {
        val hash = Archive.hash(name)

        for (entry in entries) {
            if (entry?.id == hash) {
                return entry
            }
        }
        throw FileNotFoundException("Could not find entry: $name.")
    }
    companion object {
        @Throws(IOException::class)
        fun decode(buffer: ByteBuffer): Archive {
            var buffer = buffer
            val extractedSize = BufferUtil.readUnsignedMedium(buffer)
            val size = BufferUtil.readUnsignedMedium(buffer)
            var extracted = false

            if (size != extractedSize) {
                val compressed = ByteArray(size)
                val decompressed = ByteArray(extractedSize)
                buffer[compressed]
                CompressionUtil.debzip2(compressed, decompressed)
                buffer = ByteBuffer.wrap(decompressed)
                extracted = true
            }

            val entryCount = buffer.getShort().toInt() and 0xFFFF
            val identifiers = IntArray(entryCount)
            val extractedSizes = IntArray(entryCount)
            val sizes = IntArray(entryCount)

            for (i in 0 until entryCount) {
                identifiers[i] = buffer.getInt()
                extractedSizes[i] = BufferUtil.readUnsignedMedium(buffer)
                sizes[i] = BufferUtil.readUnsignedMedium(buffer)
            }

            val entries = arrayOfNulls<ArchiveEntry>(entryCount)
            for (entry in 0 until entryCount) {
                var entryBuffer: ByteBuffer?
                if (!extracted) {
                    val compressed = ByteArray(sizes[entry])
                    val decompressed = ByteArray(extractedSizes[entry])
                    buffer[compressed]
                    CompressionUtil.debzip2(compressed, decompressed)
                    entryBuffer = ByteBuffer.wrap(decompressed)
                } else {
                    val buf = ByteArray(extractedSizes[entry])
                    buffer[buf]
                    entryBuffer = ByteBuffer.wrap(buf)
                }
                entries[entry] = ArchiveEntry(identifiers[entry], entryBuffer)
            }
            return Archive(entries)
        }

        fun hash(name: String): Int {
            return name.uppercase(Locale.getDefault()).chars().reduce(
                0
            ) { hash: Int, character: Int -> hash * 61 + character - 32 }
        }
    }
}