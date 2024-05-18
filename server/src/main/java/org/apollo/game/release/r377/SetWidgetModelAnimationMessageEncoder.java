package org.apollo.game.release.r377;

import org.apollo.game.message.impl.SetWidgetModelAnimationMessage;
import org.apollo.net.codec.game.*;
import org.apollo.net.release.MessageEncoder;

/**
 * A {@link MessageEncoder} for the {@link SetWidgetModelAnimationMessage}.
 *
 * @author Chris Fletcher
 */
public final class SetWidgetModelAnimationMessageEncoder extends MessageEncoder<SetWidgetModelAnimationMessage> {

	@Override
	public GamePacket encode(SetWidgetModelAnimationMessage message) {
		GamePacketBuilder builder = new GamePacketBuilder(2);

		builder.put(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD, message.getInterfaceId());
		builder.put(DataType.SHORT, DataTransformation.ADD, message.getAnimation() & 0xFFFF);

		return builder.toGamePacket();
	}

}