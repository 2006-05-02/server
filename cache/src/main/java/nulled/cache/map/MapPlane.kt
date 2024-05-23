package nulled.cache.map

import nulled.cache.map.Tile
import java.util.*
import java.util.stream.Stream

/**
 * A plane of a map, which is a distinct height level.
 *
 * @author Major
 */
class MapPlane(
    /**
     * The level of this MapPlane.
     */
    val level: Int, tiles: Array<Array<Tile?>>
) {
    /**
     * Gets the level of this MapPlane.
     *
     * @return The level.
     */

    /**
     * The 2-dimensional array of Tiles.
     */
    private val tiles: Array<Array<Tile?>>

    /**
     * Creates the MapPlane.
     *
     * @param level The level of the MapPlane.
     * @param tiles The 2D array of [Tile]s. Must not be `null`. Must be square.
     */
    init {
        this.tiles = clone(tiles)
    }

    val size: Int
        /**
         * Gets the amount of tiles in this MapPlane.
         *
         * @return The amount of tiles.
         */
        get() = tiles.size * tiles[0].size

    /**
     * Gets the [Tile] at the specified (x, z) coordinate.
     *
     * @param x The x coordinate.
     * @param z The z coordinate.
     * @return The Tile.
     */
    fun getTile(x: Int, z: Int): Tile? {
        return tiles[x][z]
    }

    /**
     * Gets the [Tile]s in this MapPlane.
     *
     *
     * This method returns the Tiles according on a column-based ordering: for a 2x2 tile set, the order will be
     * `(0, 0), (0, 1), (1, 0), (1, 1)`.
     *
     * @return The Tiles.
     */
    fun getTiles(): Stream<Tile> {
        return Arrays.stream(tiles).flatMap { array: Array<Tile?>? -> Arrays.stream(array) }
    }

    companion object {
        /**
         * Returns a shallow copy of the specified 2-dimensional array.
         *
         * @param array The array to copy. Must not be `null`.
         * @return The copy.
         */
        private fun <T> clone(array: Array<Array<T>>): Array<Array<T>> {
            val copy = array.clone()
            for (index in copy.indices) {
                copy[index] = array[index].clone()
            }

            return copy
        }
    }
}