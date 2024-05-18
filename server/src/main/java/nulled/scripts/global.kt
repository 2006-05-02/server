package nulled.scripts

import lostcity.engine.script.Script.Companion.FINISHED
import lostcity.engine.script.ServerTriggerType.LOGIN
import nulled.runescript.RuneScript
import nulled.scripts.tutorial.data.TutorialConstants.tutorial_complete
import nulled.scripts.tutorial.data.TutorialConstants.tutorial_progress
import nulled.scripts.tutorial.tutorial.Companion.start_tutorial
import org.apollo.game.model.World
import org.apollo.game.model.entity.Player
import org.apollo.game.model.entity.attr.AttributeDefinition
import org.apollo.game.model.entity.attr.AttributeMap
import org.apollo.game.model.entity.attr.AttributePersistence
import org.apollo.game.plugin.RuneScriptContext

class global(world: World, context: RuneScriptContext) : RuneScript(world, context) {
    init {
        AttributeMap.define("tutorial_progress", AttributeDefinition.forInt(0, AttributePersistence.PERSISTENT))

        on(LOGIN, GLOBAL) { player: Player ->
            mes(player, "Welcome to RuneScape.");
            mes(player, "The server is currently in development mode.");
            // cam_reset
            // if_close

            // music mode

            // register the soft timer that handles replenishing stats
            // same for health
            // random event timer
            // chest macro gas
            // for logout out during a general macro event, they respawn when you log back in
            // so pk skulls persist after logging back in
            // so antifire persists after logging back in
            // so antipoison persists after logging back in
            // if player is in a duel, they are removed from the duel

            if (get(player, tutorial_progress) < tutorial_complete) {
                //Either way is fine
                //player.runScript(Script(start_tutorial()))
                start_tutorial().invoke(player)
            }
            FINISHED
        }
    }
}