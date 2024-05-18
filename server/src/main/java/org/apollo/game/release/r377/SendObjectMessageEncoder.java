package org.apollo.game.release.r377;

import org.apollo.game.message.impl.SendObjectMessage;
import org.apollo.net.codec.game.*;
import org.apollo.net.release.MessageEncoder;

/**
 * A {@link MessageEncoder} for the {@link SendObjectMessage}.
 *
 * @author Major
 */
public final class SendObjectMessageEncoder extends MessageEncoder<SendObjectMessage> {

	@Override
	public GamePacket encode(SendObjectMessage message) {
		GamePacketBuilder builder = new GamePacketBuilder(152);
		builder.put(DataType.BYTE, DataTransformation.NEGATE, message.getType() << 2 | message.getOrientation());
		builder.put(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD, message.getId());
		builder.put(DataType.BYTE, DataTransformation.ADD, message.getPositionOffset());
		return builder.toGamePacket();
	}

}