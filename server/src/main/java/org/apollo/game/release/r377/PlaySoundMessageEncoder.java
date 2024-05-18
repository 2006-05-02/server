package org.apollo.game.release.r377;

import org.apollo.game.message.impl.PlaySongMessage;
import org.apollo.game.message.impl.PlaySoundMessage;
import org.apollo.net.codec.game.*;
import org.apollo.net.release.MessageEncoder;

/**
 * A {@link MessageEncoder} for the {@link PlaySongMessage}.
 *
 * @author Major
 */
public final class PlaySoundMessageEncoder extends MessageEncoder<PlaySoundMessage> {
    @Override
    public GamePacket encode(PlaySoundMessage message) {
        GamePacketBuilder builder = new GamePacketBuilder(26);
        builder.put(DataType.SHORT, message.getID());
        builder.put(DataType.BYTE, message.getLoops());
        builder.put(DataType.SHORT, message.getDelay());
        return builder.toGamePacket();
    }
}