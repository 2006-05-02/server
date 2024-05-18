package org.apollo.game.release.r377;

import org.apollo.game.message.impl.RegionChangeMessage;
import org.apollo.net.codec.game.*;
import org.apollo.net.release.MessageEncoder;

/**
 * A {@link MessageEncoder} for the {@link RegionChangeMessage}.
 *
 * @author Graham
 */
public final class RegionChangeMessageEncoder extends MessageEncoder<RegionChangeMessage> {

	@Override
	public GamePacket encode(RegionChangeMessage message) {
		GamePacketBuilder builder = new GamePacketBuilder(222);
		builder.put(DataType.SHORT, message.getPosition().getCentralRegionY());
		builder.put(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD, message.getPosition().getCentralRegionX());
		return builder.toGamePacket();
	}

}