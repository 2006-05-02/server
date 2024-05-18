package org.apollo.game.release.r377;

import org.apollo.game.message.impl.SetWidgetNpcModelMessage;
import org.apollo.net.codec.game.*;
import org.apollo.net.release.MessageEncoder;

/**
 * A {@link MessageEncoder} for the {@link SetWidgetNpcModelMessage}.
 *
 * @author Chris Fletcher
 */
public final class SetWidgetNpcModelMessageEncoder extends MessageEncoder<SetWidgetNpcModelMessage> {

	@Override
	public GamePacket encode(SetWidgetNpcModelMessage message) {
		GamePacketBuilder builder = new GamePacketBuilder(162);

		builder.put(DataType.SHORT, DataTransformation.ADD, message.getModelId());
		builder.put(DataType.SHORT, DataOrder.LITTLE, message.getInterfaceId());

		return builder.toGamePacket();
	}

}
