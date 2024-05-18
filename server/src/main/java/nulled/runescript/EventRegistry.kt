package nulled.runescript

import lostcity.engine.script.ServerTriggerType
import nulled.runescript.RuneScript.Companion.GLOBAL
import org.apollo.game.model.entity.Player

object EventRegistry {
    private val handlers = mutableMapOf<Pair<ServerTriggerType, String>, MutableList<(Player) -> Int?>>()

    fun registerEvent(triggerType: ServerTriggerType, target: String, handler: (Player) -> Int?) {
        handlers.computeIfAbsent(triggerType to target) { mutableListOf() }.add(handler)
    }

    fun triggerEvent(player: Player, triggerType: ServerTriggerType, target: String) {
        handlers[triggerType to target]?.forEach { it.invoke(player) }
    }

    fun login(player: Player) {
        triggerEvent(player, ServerTriggerType.LOGIN, GLOBAL);
    }
}