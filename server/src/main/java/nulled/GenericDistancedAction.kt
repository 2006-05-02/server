package nulled

import org.apollo.game.action.DistancedAction
import org.apollo.game.model.Position
import org.apollo.game.model.entity.Player
import org.apollo.plugins.api.dialogue.ChatNpcAction.ChatNpcEvent

class GenericDistancedAction(val player: Player, val position: Position, val action: (Player) -> Unit) :
    DistancedAction<Player>(0, true, player, position, 1) {
    companion object {
        fun start(player: Player, position: Position, action: (Player) -> Unit) {
            player.startAction(GenericDistancedAction(player, position, action))
        }
    }

    override fun executeAction() {
        if (player.world.submit(ChatNpcEvent(player))) {
            player.turnTo(position)
            action.invoke(player)
        }
        stop()
    }
}