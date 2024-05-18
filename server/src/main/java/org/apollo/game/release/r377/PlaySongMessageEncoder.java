package org.apollo.game.release.r377;

import org.apollo.game.message.impl.PlaySongMessage;
import org.apollo.net.codec.game.*;
import org.apollo.net.release.MessageEncoder;

/**
 * A {@link MessageEncoder} for the {@link PlaySongMessage}.
 *
 * @author Major
 */
public final class PlaySongMessageEncoder extends MessageEncoder<PlaySongMessage> {
	@Override
	public GamePacket encode(PlaySongMessage message) {
		GamePacketBuilder builder = new GamePacketBuilder(249);
		builder.put(DataType.SHORT, DataOrder.LITTLE, message.getID());
		builder.put(DataType.TRI_BYTE, DataOrder.LITTLE, message.getDelay());
		return builder.toGamePacket();
	}
}