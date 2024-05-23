package nulled.cache.map

/**
 * Represents a static world object in a map file.
 */
class MapObject
/**
 * Creates a new `MapObject`.
 *
 * @param id The object ID of this map object.
 * @param packedCoordinates A packed integer containing the coordinates of this map object.
 * @param type The type of object.
 * @param orientation The object facing direction.
 */(
    /**
     * The object definition id of this `MapObject`.
     */
    val id: Int,
    /**
     * The packed coordinates (local XY and height) for this object.
     */
    val packedCoordinates: Int,
    /**
     * The type of this object.
     */
    val type: Int,
    /**
     * The orientation of this object.
     */
    val orientation: Int
) {
    /**
     * Get the object ID of this map object.
     *
     * @return The object ID for [LocDefinition] lookups.
     */

    /**
     * Get a packed integer containing the x/y coordinates and height for this object.
     *
     * @return The packed coordinates.
     */

    /**
     * Get the type of this object.
     *
     * @return The type of this object.
     */

    /**
     * Get the integer representation of this objects orientation (0 indexed, starting West-North-East-South).
     *
     * @return The orientation of this object.
     */

    /**
     * Create a new `MapObject`.
     *
     * @param id The object ID of this map object.
     * @param x The local X coordinate of this object.
     * @param y The local Y coordinate of this object.
     * @param height The height level of this object.
     * @param type The type of this object.
     * @param orientation The orientation of this object.
     */
    constructor(id: Int, x: Int, y: Int, height: Int, type: Int, orientation: Int) : this(
        id,
        (height and 0x3f) shl 12 or ((x and 0x3f) shl 6) or (y and 0x3f),
        type,
        orientation
    )

    val height: Int
        /**
         * Get the plane this map object exists on.
         *
         * @return The plane this map object is on.
         */
        get() = packedCoordinates shr 12 and 0x3

    val localX: Int
        /**
         * Get the X coordinate of this object relative to the map position.
         *
         * @return The local X coordinate.
         */
        get() = packedCoordinates shr 6 and 0x3F

    val localY: Int
        /**
         * Get the Y coordinate of this object relative to the map position.
         *
         * @return The local Y coordinate.
         */
        get() = packedCoordinates and 0x3F
}
