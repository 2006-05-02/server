package nulled.cache.map

import nulled.cache.fs.IndexedFileSystem
import java.io.IOException
import java.io.UncheckedIOException

/**
 * Decodes [MapIndex]s from the [IndexedFileSystem].
 *
 * @author Ryley
 * @author Major
 */
class MapIndexDecoder(
    /**
     * The IndexedFileSystem.
     */
    private val fs: IndexedFileSystem
) : Runnable {
    /**
     * Decodes [MapIndex]s from the specified [IndexedFileSystem].
     *
     * @return A [Map] of packed coordinates to their MapDefinitions.
     * @throws IOException If there is an error reading or decoding the Archive.
     */
    @Throws(IOException::class)
    fun decode(): Map<Int, MapIndex> {
        val archive = fs.getArchive(0, VERSIONS_ARCHIVE_FILE_ID)
        val entry = archive.getEntry("map_index")
        val definitions: MutableMap<Int, MapIndex> = HashMap()

        val buffer = entry.buffer
        val count = buffer.capacity() / (3 * java.lang.Short.BYTES + java.lang.Byte.BYTES)

        for (times in 0 until count) {
            val id = buffer.getShort().toInt() and 0xFFFF
            val terrain = buffer.getShort().toInt() and 0xFFFF
            val objects = buffer.getShort().toInt() and 0xFFFF
            val members = buffer.get().toInt() == 1

            definitions[id] = MapIndex(id, terrain, objects, members)
        }

        return definitions
    }

    override fun run() {
        try {
			MapIndex.init(decode())
        } catch (e: IOException) {
            throw UncheckedIOException(e)
        }
    }

    companion object {
        /**
         * The file id of the versions archive.
         */
        private const val VERSIONS_ARCHIVE_FILE_ID = 5
    }
}