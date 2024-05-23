package nulled.cache.map

/**
 * A single tile on the map.
 *
 * @author Major
 */
class Tile
/**
 * Creates the Tile.
 *
 * @param x The x coordinate of the Tile.
 * @param y The y coordinate of the Tile.
 * @param attributes The attributes.
 * @param height The height.
 * @param overlay The overlay id.
 * @param overlayType The overlay type.
 * @param overlayOrientation The overlay orientation.
 * @param underlay The underlay id.
 */(
    /**
     * The x coordinate of this Tile.
     */
    val x: Int,
    /**
     * The y coordinate of this Tile.
     */
    val y: Int,
    /**
     * The attributes of this Tile.
     */
    val attributes: Int,
    /**
     * The height of this Tile.
     */
    val height: Int,
    /**
     * The overlay id of this Tile.
     */
    val overlay: Int,
    /**
     * The overlay type of this Tile.
     */
    val overlayType: Int,
    /**
     * The overlay orientation of this Tile.
     */
    val overlayOrientation: Int,
    /**
     * The underlay id of this Tile.
     */
    val underlay: Int
) {
    /**
     * A builder class for a Tile.
     */
    class Builder
    /**
     * Creates the Builder.
     *
     * @param x The x position of the Tile.
     * @param y The y position of the Tile.
     * @param height The height level of the Tile.
     */(
        /**
         * The x coordinate of the Tile.
         */
        private var x: Int,
        /**
         * The y coordinate of the Tile.
         */
        private var y: Int,
        /**
         * The height of the Tile.
         */
        private var height: Int
    ) {
        /**
         * The attributes of the Tile.
         */
        private var attributes = 0

        /**
         * The overlay id of the Tile.
         */
        private var overlay = 0

        /**
         * The overlay orientation of the Tile.
         */
        private var overlayOrientation = 0

        /**
         * The overlay type of the Tile.
         */
        private var overlayType = 0

        /**
         * The underlay id of the Tile.
         */
        private var underlay = 0

        /**
         * Builds the contents of this Builder into a Tile.
         *
         * @return The Tile.
         */
        fun build(): Tile {
            return Tile(x, y, attributes, height, overlay, overlayType, overlayOrientation, underlay)
        }

        /**
         * Sets the attributes of the Tile.
         *
         * @param attributes The attributes.
         */
        fun setAttributes(attributes: Int) {
            this.attributes = attributes
        }

        /**
         * Sets the height of the Tile.
         *
         * @param height The height.
         */
        fun setHeight(height: Int) {
            this.height = height
        }

        /**
         * Sets the overlay id of the Tile.
         *
         * @param overlay The overlay id.
         */
        fun setOverlay(overlay: Int) {
            this.overlay = overlay
        }

        /**
         * Sets the overlay orientation of the Tile.
         *
         * @param orientation The overlay orientation.
         */
        fun setOverlayOrientation(orientation: Int) {
            this.overlayOrientation = orientation
        }

        /**
         * Sets the overlay type of the Tile.
         *
         * @param type The overlay type.
         */
        fun setOverlayType(type: Int) {
            this.overlayType = type
        }

        /**
         * Sets the position of the Tile.
         *
         * @param x The x coordinate of the Tile.
         * @param y the y coordinate of the Tile
         * @param height The height level of the Tile.
         */
        fun setPosition(x: Int, y: Int, height: Int) {
            this.x = x
            this.y = y
            this.height = height
        }

        /**
         * Sets the underlay id of the Tile.
         *
         * @param underlay The underlay.
         */
        fun setUnderlay(underlay: Int) {
            this.underlay = underlay
        }
    }

    /**
     * Gets the attributes of this Tile.
     *
     * @return The attributes.
     */

    /**
     * Gets the height of this Tile.
     *
     * @return The height.
     */

    /**
     * Gets the overlay id of this Tile.
     *
     * @return The overlay id.
     */

    /**
     * Gets the overlay orientation of this Tile.
     *
     * @return The overlay orientation.
     */

    /**
     * Gets the overlay type of this Tile.
     *
     * @return The overlay types.
     */

    /**
     * Gets the x coordinate of this Tile.
     *
     * @return The x coordinate.
     */

    /**
     * Gets the y coordinate of this Tile.
     *
     * @return The y coordinate.
     */

    /**
     * Gets the underlay id of this Tile.
     *
     * @return The underlay id.
     */


    companion object {
        /**
         * Creates a [Builder] for a Tile.
         *
         * @param x The x coordinate of the Tile.
         * @param y the y coordinate of the Tile.
         * @param height The height level of the Tile.
         * @return The Builder.
         */
        fun builder(x: Int, y: Int, height: Int): Builder {
            return Builder(x, y, height)
        }
    }
}
