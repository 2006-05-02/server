package org.apollo.game.release.r377;

import org.apollo.game.message.impl.ItemActionMessage;
import org.apollo.net.codec.game.*;
import org.apollo.net.release.MessageDecoder;

/**
 * A {@link MessageDecoder} for the second {@link ItemActionMessage}.
 *
 * @author Graham
 */
public final class SecondItemActionMessageDecoder extends MessageDecoder<ItemActionMessage> {

	@Override
	public ItemActionMessage decode(GamePacket packet) {
		GamePacketReader reader = new GamePacketReader(packet);

		int slot = (int) reader.getUnsigned(DataType.SHORT, DataTransformation.ADD);
		int id = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE);
		int interfaceId = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE);

		return new ItemActionMessage(2, interfaceId, id, slot);
	}

}