package org.apollo.plugins.api.dialogue

import org.apollo.game.action.DistancedAction
import org.apollo.game.model.Position
import org.apollo.game.model.entity.Player
import org.apollo.game.model.event.PlayerEvent
import org.apollo.net.message.Message

class ChatNpcAction(
    private val player: Player,
    position: Position,
    private val action: (Player) -> Unit
) :
    DistancedAction<Player>(0, true, player, position, 1) {
    companion object {
        fun start(message: Message, player: Player, position: Position, action: (Player) -> Unit) {
            player.startAction(ChatNpcAction(player, position, action))
            message.terminate()
        }

        fun start(player: Player, position: Position, action: (Player) -> Unit) {
            player.startAction(ChatNpcAction(player, position, action))
        }
    }

    override fun executeAction() {
        if (player.world.submit(ChatNpcEvent(player))) {
            player.turnTo(position)
            action.invoke(player)
        }
        stop()
    }

    override fun equals(other: Any?): Boolean {
        return other is ChatNpcAction && position == other.position && player == other.player
    }

    override fun hashCode(): Int = position.hashCode() * 31 + player.hashCode()

    class ChatNpcEvent(player: Player) : PlayerEvent(player)
}