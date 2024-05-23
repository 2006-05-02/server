package nulled.cache.map

import java.util.*

/**
 * A definition for a map.
 */
class MapIndex
/**
 * Creates the [MapIndex].
 *
 * @param packedCoordinates The packed coordinates.
 * @param terrain The terrain file id.
 * @param objects The object file id.
 * @param members Indicates whether or not this map is members-only.
 */(
    /**
     * The packed coordinates.
     */
    val packedCoordinates: Int,
    /**
     * The terrain file id.
     */
    val mapFile: Int,
    /**
     * The object file id.
     */
    val objectFile: Int,
    /**
     * Indicates whether or not this map is members-only.
     */
    val isMembersOnly: Boolean
) {
    /**
     * Returns whether or not this MapIndex is for a members-only area of the world.
     *
     * @return `true` if this MapIndex is for a members-only area, `false` if not.
     */

    /**
     * Gets the id of the file containing the object data.
     *
     * @return The file id.
     */

    /**
     * Gets the packed coordinates.
     *
     * @return The packed coordinates.
     */

    /**
     * Gets the id of the file containing the terrain data.
     *
     * @return The file id.
     */

    val x: Int
        /**
         * Gets the X coordinate of this map.
         *
         * @return The X coordinate of this map.
         */
        get() = (packedCoordinates shr 8 and 0xFF) * MapConstants.MAP_WIDTH

    val y: Int
        /**
         * Gets the Y coordinate of this map.
         *
         * @return The y coordinate of this map.
         */
        get() = (packedCoordinates and 0xFF) * MapConstants.MAP_WIDTH

    companion object {
        /**
         * Gets the `Map` of [MapIndex] instances.
         *
         * @return The map of [MapIndex] instances.
         */
        /**
         * A mapping of region ids to [MapIndex]es.
         */
        var indices: Map<Int, MapIndex>? = null
            private set

        /**
         * Initialises the class with the specified set of indices.
         */
        fun init(indices: Map<Int, MapIndex>) {
            Companion.indices = Collections.unmodifiableMap(indices)
        }
    }
}
