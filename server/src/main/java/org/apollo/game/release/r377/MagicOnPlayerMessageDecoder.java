package org.apollo.game.release.r377;

import org.apollo.game.message.impl.MagicOnPlayerMessage;
import org.apollo.net.codec.game.*;
import org.apollo.net.release.MessageDecoder;

/**
 * A {@link MessageDecoder} for the {@link MagicOnPlayerMessage}
 *
 * @author Stuart
 */
public final class MagicOnPlayerMessageDecoder extends MessageDecoder<MagicOnPlayerMessage> {

	@Override
	public MagicOnPlayerMessage decode(GamePacket packet) {
		GamePacketReader reader = new GamePacketReader(packet);

		int index = (int) reader.getUnsigned(DataType.SHORT, DataTransformation.ADD);
		int spell = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE);

		return new MagicOnPlayerMessage(index, spell);
	}

}