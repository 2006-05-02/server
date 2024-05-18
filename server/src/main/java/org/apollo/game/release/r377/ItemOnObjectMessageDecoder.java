package org.apollo.game.release.r377;

import org.apollo.game.message.impl.ItemOnObjectMessage;
import org.apollo.net.codec.game.*;
import org.apollo.net.release.MessageDecoder;

/**
 * A {@link MessageDecoder} for the {@link ItemOnObjectMessage}.
 *
 * @author Major
 */
public final class ItemOnObjectMessageDecoder extends MessageDecoder<ItemOnObjectMessage> {

	@Override
	public ItemOnObjectMessage decode(GamePacket packet) {
		GamePacketReader reader = new GamePacketReader(packet);

		int objectId = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE);
		int interfaceId = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE);
		int itemId = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE);
		int y = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE);
		int slot = (int) reader.getUnsigned(DataType.SHORT);
		int x = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD);

		return new ItemOnObjectMessage(interfaceId, itemId, slot, objectId, x, y);
	}

}