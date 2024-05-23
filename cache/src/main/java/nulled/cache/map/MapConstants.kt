package nulled.cache.map

/**
 * Contains [MapFile]-related constants.
 *
 * @author Major
 */
object MapConstants {
    /**
     * The index containing the map files.
     */
    const val MAP_INDEX: Int = 4

    /**
     * The width (and length) of a [MapFile] in [Tile]s.
     */
    const val MAP_WIDTH: Int = 64

    /**
     * The amount of planes in a MapFile.
     */
    const val MAP_PLANES: Int = 4

    /**
     * The multiplicand for height values.
     */
    const val HEIGHT_MULTIPLICAND: Int = 8

    /**
     * The lowest type value that will result in the decoding of a Tile being continued.
     */
    const val LOWEST_CONTINUED_TYPE: Int = 2

    /**
     * The minimum type that specifies the Tile attributes.
     */
    const val MINIMUM_ATTRIBUTES_TYPE: Int = 81

    /**
     * The minimum type that specifies the Tile underlay id.
     */
    const val MINIMUM_OVERLAY_TYPE: Int = 49

    /**
     * The amount of possible overlay orientations.
     */
    const val ORIENTATION_COUNT: Int = 4

    /**
     * The height difference between two planes.
     */
    const val PLANE_HEIGHT_DIFFERENCE: Int = 240
}