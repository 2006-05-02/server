package org.apollo.game

/**
 * Contains game-related constants.
 *
 * @author Graham
 */
object GameConstants {
    /**
     * The maximum amount of messages to process per pulse (per session).
     */
    const val MESSAGES_PER_PULSE: Int = 25

    /**
     * The delay between consecutive pulses, in milliseconds.
     */
    const val PULSE_DELAY: Int = 600
}