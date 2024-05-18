package org.apollo.game.release.r377;

import org.apollo.game.message.impl.OpenTutorialDialogueInterfaceMessage;
import org.apollo.net.codec.game.*;
import org.apollo.net.release.MessageEncoder;

/**
 * A {@link MessageEncoder} for the {@link OpenTutorialDialogueInterfaceMessage}.
 *
 * @author Null
 */
public final class OpenTutorialDialogueInterfaceMessageEncoder extends MessageEncoder<OpenTutorialDialogueInterfaceMessage> {

	@Override
	public GamePacket encode(OpenTutorialDialogueInterfaceMessage message) {
		GamePacketBuilder builder = new GamePacketBuilder(158);
		builder.put(DataType.SHORT, DataOrder.LITTLE, message.getInterfaceId());
		return builder.toGamePacket();
	}

}