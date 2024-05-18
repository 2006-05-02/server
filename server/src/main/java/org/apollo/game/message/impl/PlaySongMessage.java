package org.apollo.game.message.impl;

import org.apollo.net.message.Message;

/**
 * A {@link Message} sent to the client to update the track to be played.
 *
 * @author Null
 */
public final class PlaySongMessage extends Message {

	/**
	 * id of the track
	 */
	private final int id;

	/**
	 * delay before starting track
	 */
	private final int delay;

	public PlaySongMessage(int id, int delay) {
		this.id = id;
		this.delay = delay;
	}

	public int getID() {
		return id;
	}

	public int getDelay() {
		return delay;
	}

}