package org.apollo.game.release.r377;

import org.apollo.game.message.impl.SetWidgetPlayerModelMessage;
import org.apollo.net.codec.game.*;
import org.apollo.net.release.MessageEncoder;

/**
 * A {@link MessageEncoder} for the {@link SetWidgetPlayerModelMessage}.
 *
 * @author Chris Fletcher
 */
public final class SetWidgetPlayerModelMessageEncoder extends MessageEncoder<SetWidgetPlayerModelMessage> {

	@Override
	public GamePacket encode(SetWidgetPlayerModelMessage message) {
		GamePacketBuilder builder = new GamePacketBuilder(255);
		builder.put(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD, message.getInterfaceId());
		return builder.toGamePacket();
	}

}