package org.apollo.game.release.r377;

import org.apollo.game.message.impl.ItemOptionMessage;
import org.apollo.net.codec.game.*;
import org.apollo.net.release.MessageDecoder;

/**
 * A {@link MessageDecoder} for the fourth {@link ItemOptionMessage}.
 *
 * @author Chris Fletcher
 */
public final class FourthItemOptionMessageDecoder extends MessageDecoder<ItemOptionMessage> {

	@Override
	public ItemOptionMessage decode(GamePacket packet) {
		GamePacketReader reader = new GamePacketReader(packet);

		int slot = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE);
		int id = (int) reader.getUnsigned(DataType.SHORT, DataTransformation.ADD);
		int interfaceId = (int) reader.getUnsigned(DataType.SHORT);

		return new ItemOptionMessage(4, interfaceId, id, slot);
	}

}