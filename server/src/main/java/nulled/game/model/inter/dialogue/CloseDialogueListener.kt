import org.apollo.game.message.impl.CloseInterfaceMessage
import org.apollo.game.model.entity.Player
import org.apollo.game.model.inter.dialogue.DialogueListener

object CloseDialogueListener : DialogueListener {
    override fun interfaceClosed() {

    }

    override fun buttonClicked(button: Int): Boolean {
        return true
    }

    override fun continued(player: Player) {
        player.send(CloseInterfaceMessage())
    }
}