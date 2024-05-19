package nulled

import org.apollo.game.action.Action
import org.apollo.game.action.DistancedAction
import org.apollo.game.model.Position
import org.apollo.game.model.entity.Player
import org.apollo.game.model.event.PlayerEvent
import org.apollo.plugins.api.dialogue.ChatNpcAction.ChatNpcEvent

class GenericTimedAction(val player: Player, val delay: Int, val action: (Player) -> Unit) :
    Action<Player>(delay, false, player) {
    companion object {
        fun start(player: Player, delay: Int, action: (Player) -> Unit) {
            player.startAction(GenericTimedAction(player, delay, action))
        }
    }

    override fun execute() {
        println("timed action")
        if (player.world.submit(GenericTimedEvent(player))) {
            action.invoke(player)
        }
        stop()
    }

    class GenericTimedEvent(player: Player) : PlayerEvent(player)
}