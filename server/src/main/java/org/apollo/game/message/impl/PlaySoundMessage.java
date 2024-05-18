package org.apollo.game.message.impl;

import org.apollo.net.message.Message;

/**
 * A {@link Message} sent to the client to update the sound to be played.
 *
 * @author Null
 */
public final class PlaySoundMessage extends Message {

	/**
	 * id of the track
	 */
	private final int id;

	/**
	 * id of the track
	 */
	private final int loops;

	/**
	 * delay before starting track
	 */
	private final int delay;

	public PlaySoundMessage(int id, int loops, int delay) {
		this.id = id;
		this.loops = loops;
		this.delay = delay;
	}

	public int getID() {
		return id;
	}

	public int getLoops() {return loops;}

	public int getDelay() {
		return delay;
	}

}