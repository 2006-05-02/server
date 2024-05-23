package nulled.cache.map

import nulled.cache.fs.IndexedFileSystem
import org.apollo.util.BufferUtil
import org.apollo.util.CompressionUtil
import java.io.IOException
import java.nio.ByteBuffer

/**
 * A decoder for reading the map objects for a given map.
 *
 * @author Major
 */
class MapObjectsDecoder(buffer: ByteBuffer) {
    /**
     * The buffer to decode [MapObject]s from.
     */
    private val buffer: ByteBuffer = buffer.asReadOnlyBuffer()

    /**
     * Decodes the data in the `buffer` to a list of [MapObject]s.
     *
     * @return A list of decoded [MapObject]s.
     */
    fun decode(): List<MapObject> {
        val objects: MutableList<MapObject> = ArrayList()

        var id = -1
        var idOffset = BufferUtil.readSmart(buffer)

        while (idOffset != 0) {
            id += idOffset

            var packed = 0
            var positionOffset = BufferUtil.readSmart(buffer)

            while (positionOffset != 0) {
                packed += positionOffset - 1

                val attributes = buffer.get().toInt() and 0xFF
                val type = attributes shr 2
                val orientation = attributes and 0x3
                objects.add(MapObject(id, packed, type, orientation))

                positionOffset = BufferUtil.readSmart(buffer)
            }

            idOffset = BufferUtil.readSmart(buffer)
        }

        return objects
    }

    companion object {
        /**
         * Creates a MapObjectsDecoder for the specified map file.
         *
         * @param fs The [IndexedFileSystem] to get the file from.
         * @param index The map index to decode objects for.
         * @return The MapObjectsDecoder.
         * @throws IOException If there is an error reading or decompressing the file.
         */
        @Throws(IOException::class)
        fun create(fs: IndexedFileSystem, index: MapIndex): MapObjectsDecoder {
            val compressed = fs.getFile(MapConstants.MAP_INDEX, index.objectFile)
            val decompressed = ByteBuffer.wrap(CompressionUtil.degzip(compressed))

            return MapObjectsDecoder(decompressed)
        }
    }
}
