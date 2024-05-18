package nulled.scripts.tutorial

import lostcity.engine.script.Script
import lostcity.engine.script.Script.Companion.FINISHED
import lostcity.engine.script.ServerTriggerType
import nulled.GenericDistancedAction
import nulled.runescript.RuneScript
import nulled.runescript.ScriptManager.Companion.runScript
import nulled.scripts.doors.open_and_close_doors.Companion.open_and_close_door
import nulled.scripts.tutorial.data.TutorialConstants.runescape_guide_interact_with_scenery
import nulled.scripts.tutorial.data.TutorialConstants.runescape_guide_interacted_with_door
import nulled.scripts.tutorial.data.TutorialConstants.tutorial_progress
import nulled.scripts.tutorial.tutorial.Companion.tutorial_step_moving_around
import org.apollo.game.model.World
import org.apollo.game.model.entity.Player
import org.apollo.game.plugin.RuneScriptContext

class tut_doors_and_gates(world: World, context: RuneScriptContext) : RuneScript(world, context) {
    init {
        on(ServerTriggerType.OPLOC1, "loc_3014") { player: Player ->
            if (get(player, tutorial_progress) < runescape_guide_interact_with_scenery) {
                mesbox(player, "You need to talk to the 'Runescape Guide'|before you are allowed to proceed through this door.");
                return@on FINISHED
            }
            println("not finished")
            val outside = (player.position.x >= 3098)
            if (!outside) {
                GenericDistancedAction.start(player, player.loc.position) {
                    player.runScript(Script(open_and_close_door(null, false, false)))
                    if (get(player, tutorial_progress) == runescape_guide_interact_with_scenery) {
                        set(player, tutorial_progress, runescape_guide_interacted_with_door)
                        tutorial_step_moving_around().invoke(player)
                    }
                }
            }
            FINISHED
        }
    }
}