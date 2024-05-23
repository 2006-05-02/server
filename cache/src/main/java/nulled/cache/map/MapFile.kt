package nulled.cache.map

import com.google.common.base.Preconditions

/**
 * A 3-dimensional 64x64 area of the map.
 *
 * @author Major
 */
class MapFile(planes: Array<MapPlane?>) {
    /**
     * The array of MapPlanes.
     */
    private val planes = planes.clone()

    /**
     * Gets the [MapPlane] with the specified level.
     *
     * @param plane The plane.
     * @return The MapPlane.
     * @throws ArrayIndexOutOfBoundsException If `plane` is out of bounds.
     */
    fun getPlane(plane: Int): MapPlane? {
        val length = planes.size
        Preconditions.checkElementIndex(plane, length, "Plane index out of bounds, must be [0, $length).")
        return planes[plane]
    }

    /**
     * Gets all of the [MapPlane]s in this MapFile.
     *
     * @return The MapPlanes.
     */
    fun getPlanes(): Array<MapPlane?> {
        return planes.clone()
    }
}