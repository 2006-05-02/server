package org.apollo.plugins.api.dialogue

import org.apollo.game.model.entity.Npc
import org.apollo.game.model.entity.Player
import org.apollo.game.model.inter.dialogue.DialogueListener

open class DialogueResponse(val npc: Npc) : DialogueListener {
    open fun send(player: Player) {}

    override fun interfaceClosed() {}

    override fun buttonClicked(button: Int): Boolean {
        return false
    }

    override fun continued(player: Player) {}
}