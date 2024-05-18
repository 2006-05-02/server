package nulled.scripts.debug

import lostcity.engine.script.Script.Companion.FINISHED
import lostcity.engine.script.ServerTriggerType
import nulled.runescript.RuneScript
import org.apollo.game.model.World
import org.apollo.game.model.entity.Player
import org.apollo.game.plugin.RuneScriptContext

class debug_loc(world: World, context: RuneScriptContext) : RuneScript(world, context) {
    init {
        on(ServerTriggerType.OPLOC1, "_") { player: Player ->
            println("${player.loc.definition.name} pos:${player.loc.position}")
            return@on FINISHED
        }
    }
}