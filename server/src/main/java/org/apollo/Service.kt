package org.apollo


/**
 * Represents a service that the server provides for a World.
 *
 * @author Graham
 */
abstract class Service {
    var context: ServerContext? = null

    /**
     * Starts the service.
     */
    abstract fun start()
}