package org.apollo

import org.apollo.game.model.World
import org.apollo.game.service.GameService
import org.apollo.game.service.LoginService
import org.apollo.game.service.UpdateService
import java.util.logging.Logger

/**
 * A class which manages [Service]s.
 *
 * @author Graham
 * @author Major
 */
class ServiceManager(world: World?) {
    /**
     * Gets the [GameService].
     *
     * @return The GameService.
     */
    /**
     * The GameService.
     */
    val game: GameService = GameService(world)

    /**
     * Gets the [LoginService].
     *
     * @return The LoginService.
     */
    /**
     * The LoginService.
     */
    val login: LoginService = LoginService(world)

    /**
     * Gets the [UpdateService].
     *
     * @return The UpdateService.
     */
    /**
     * The UpdateService.
     */
    val update: UpdateService = UpdateService()

    /**
     * Sets the context of all services.
     *
     * @param context The server context.
     */
    fun setContext(context: ServerContext?) {
        game.context = context
        login.context = context
        update.context = context
    }

    /**
     * Starts all the services.
     */
    fun startAll() {
        logger.info("Starting services...")
        game.start()
        login.start()
        update.start()
    }

    companion object {
        /**
         * The Logger for this class.
         */
        private val logger: Logger = Logger.getLogger(ServiceManager::class.java.name)
    }
}