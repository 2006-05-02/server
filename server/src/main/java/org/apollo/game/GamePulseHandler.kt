package org.apollo.game

import org.apollo.game.service.GameService
import java.util.logging.Level
import java.util.logging.Logger

/**
 * A class which handles the logic for each pulse of the [GameService].
 *
 * @author Graham
 */
class GamePulseHandler
/**
 * Creates the GamePulseHandler.
 *
 * @param service The [GameService].
 */(
    /**
     * The [GameService].
     */
    private val service: GameService
) : Runnable {
    override fun run() {
        try {
            service.pulse()
        } catch (throwable: Throwable) {
            logger.log(Level.SEVERE, "Exception occurred during pulse!", throwable)
        }
    }

    companion object {
        /**
         * The Logger for this class.
         */
        private val logger: Logger = Logger.getLogger(GamePulseHandler::class.java.name)
    }
}