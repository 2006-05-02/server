package nulled.cache.map

import nulled.cache.fs.IndexedFileSystem
import org.apollo.util.CompressionUtil
import java.io.IOException
import java.nio.ByteBuffer

/**
 * A decoder for the terrain data stored in [MapFile]s.
 *
 * @author Major
 */
class MapFileDecoder(buffer: ByteBuffer) {
    /**
     * The DataBuffer containing the MapFile data.
     */
    private val buffer: ByteBuffer = buffer.asReadOnlyBuffer()

    /**
     * Decodes the data into a [MapFile].
     *
     * @return The MapFile.
     */
    fun decode(): MapFile {
        val planes = arrayOfNulls<MapPlane>(MapConstants.MAP_PLANES)

        for (level in 0 until MapConstants.MAP_PLANES) {
            planes[level] = decodePlane(planes, level)
        }

        return MapFile(planes)
    }

    /**
     * Decodes a [MapPlane] with the specified level.
     *
     * @param planes The previously-decoded [MapPlane]s, for calculating the height of the tiles.
     * @param level The level.
     * @return The MapPlane.
     */
    private fun decodePlane(planes: Array<MapPlane?>, level: Int): MapPlane {
        val tiles = Array(MapConstants.MAP_WIDTH) { arrayOfNulls<Tile>(MapConstants.MAP_WIDTH) }

        for (x in 0 until MapConstants.MAP_WIDTH) {
            for (z in 0 until MapConstants.MAP_WIDTH) {
                tiles[x][z] = decodeTile(planes, level, x, z)
            }
        }

        return MapPlane(level, tiles)
    }

    /**
     * Decodes the data into a [Tile].
     *
     * @param planes The previously-decoded [MapPlane]s, for calculating the height of the Tile.
     * @param level The level the Tile is on.
     * @param x The x coordinate of the Tile.
     * @param z The z coordinate of the Tile.
     * @return The MapFile.
     */
    private fun decodeTile(planes: Array<MapPlane?>, level: Int, x: Int, z: Int): Tile {
        val builder: Tile.Builder = Tile.builder(x, z, level)

        var type: Int
        do {
            type = buffer.get().toInt() and 0xFF

            if (type == 0) {
                if (level == 0) {
                    builder.setHeight(TileUtils.calculateHeight(x, z))
                } else {
                    val below = planes[level - 1]!!.getTile(x, z)!!
                    builder.setHeight(below.height + MapConstants.PLANE_HEIGHT_DIFFERENCE)
                }
            } else if (type == 1) {
                val height = buffer.get().toInt()
                val below = if ((level == 0)) 0 else planes[level - 1]!!.getTile(x, z)!!.height

                builder.setHeight((if (height == 1) 0 else height) * MapConstants.HEIGHT_MULTIPLICAND + below)
            } else if (type <= MapConstants.MINIMUM_OVERLAY_TYPE) {
                builder.setOverlay(buffer.get().toInt())
                builder.setOverlayType(
                    ((type - MapConstants.LOWEST_CONTINUED_TYPE)
                            / MapConstants.ORIENTATION_COUNT)
                )
                builder.setOverlayOrientation(
                    (type - MapConstants.LOWEST_CONTINUED_TYPE
                            % MapConstants.ORIENTATION_COUNT)
                )
            } else if (type <= MapConstants.MINIMUM_ATTRIBUTES_TYPE) {
                builder.setAttributes(type - MapConstants.MINIMUM_OVERLAY_TYPE)
            } else {
                builder.setUnderlay(type - MapConstants.MINIMUM_ATTRIBUTES_TYPE)
            }
        } while (type >= MapConstants.LOWEST_CONTINUED_TYPE)

        return builder.build()
    }

    companion object {
        /**
         * Creates a MapFileDecoder for the specified map file.
         *
         * @param fs The [IndexedFileSystem] to get the file from.
         * @param index The [MapIndex] to get the file index from.
         * @return The MapFileDecoder.
         * @throws IOException If there is an error reading or decompressing the file.
         */
        @Throws(IOException::class)
        fun create(fs: IndexedFileSystem, index: MapIndex): MapFileDecoder {
            val compressed = fs.getFile(MapConstants.MAP_INDEX, index.mapFile)
            val decompressed = ByteBuffer.wrap(CompressionUtil.degzip(compressed))

            return MapFileDecoder(decompressed)
        }
    }
}
