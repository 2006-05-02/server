package nulled.runescript

import lostcity.engine.script.ServerTriggerType
import nulled.runescript.EventRegistry.triggerEvent
import nulled.runescript.RuneScript.Companion.GLOBAL
import org.apollo.game.message.impl.ButtonMessage
import org.apollo.game.message.impl.CloseInterfaceMessage
import org.apollo.game.message.impl.NpcActionMessage
import org.apollo.game.message.impl.ObjectActionMessage
import org.apollo.game.message.impl.PlayerDesignMessage
import org.apollo.game.model.entity.Npc
import org.apollo.game.model.entity.Player
import org.apollo.game.model.entity.obj.GameObject
import org.apollo.game.plugin.api.findObject
import org.apollo.net.message.Message
import java.util.*

object MessageHandler {
    fun handleMessage(player: Player, message: Message) : Boolean {
        if (message is NpcActionMessage) {
            val npc: Npc = player.world.npcRepository.get(message.index)
            player.npc = npc
            when (message.option) {
                1 -> {
                    triggerEvent(player, ServerTriggerType.OPNPC1, GLOBAL)
                    triggerEvent(player, ServerTriggerType.OPNPC1, formatNPCName(npc.definition.name))
                }
                2 -> {
                    triggerEvent(player, ServerTriggerType.OPNPC2, GLOBAL)
                    triggerEvent(player, ServerTriggerType.OPNPC2, formatNPCName(npc.definition.name))
                }
                3 -> {
                    triggerEvent(player, ServerTriggerType.OPNPC3, GLOBAL)
                    triggerEvent(player, ServerTriggerType.OPNPC3, formatNPCName(npc.definition.name))
                }
                4 -> {
                    triggerEvent(player, ServerTriggerType.OPNPC4, GLOBAL)
                    triggerEvent(player, ServerTriggerType.OPNPC4, formatNPCName(npc.definition.name))
                }
                5 -> {
                    triggerEvent(player, ServerTriggerType.OPNPC5, GLOBAL)
                    triggerEvent(player, ServerTriggerType.OPNPC5, formatNPCName(npc.definition.name))
                }
                else -> return false
            }
            return true
        } else if (message is ObjectActionMessage) {
            val loc: GameObject = player.world.findObject(message.position, message.id) ?: return false
            player.loc = loc
            when (message.option) {
                1 -> {
                    triggerEvent(player, ServerTriggerType.OPLOC1, GLOBAL)
                    triggerEvent(player, ServerTriggerType.OPLOC1, formatLocName(message.id))
                }
                2 -> {
                    triggerEvent(player, ServerTriggerType.OPLOC2, GLOBAL)
                    triggerEvent(player, ServerTriggerType.OPLOC2, formatLocName(message.id))
                }
                3 -> {
                    triggerEvent(player, ServerTriggerType.OPLOC3, GLOBAL)
                    triggerEvent(player, ServerTriggerType.OPLOC3, formatLocName(message.id))
                }
                4 -> {
                    triggerEvent(player, ServerTriggerType.OPLOC4, GLOBAL)
                    triggerEvent(player, ServerTriggerType.OPLOC4, formatLocName(message.id))
                }
                5 -> {
                    triggerEvent(player, ServerTriggerType.OPLOC5, GLOBAL)
                    triggerEvent(player, ServerTriggerType.OPLOC5, formatLocName(message.id))
                }
                else -> return false
            }
            return true
        } else if (message is CloseInterfaceMessage) {
            triggerEvent(player, ServerTriggerType.IF_CLOSE, GLOBAL)
            return true
        } else if (message is ButtonMessage) {
            triggerEvent(player, ServerTriggerType.IF_BUTTON, GLOBAL)
            triggerEvent(player, ServerTriggerType.IF_BUTTON, message.widgetId.toString())
            return true
        } else if (message is PlayerDesignMessage) {
            if (player.canDesign)
                player.appearance = message.appearance
            return true
        }
        return false
    }

    private fun formatNPCName(name: String): String {
        val lower = name.lowercase(Locale.getDefault())
        return lower.replace(" ", "_")
    }

    private fun formatLocName(id: Int): String {
        return "loc_$id"
    }
}