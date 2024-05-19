package nulled.scripts.tutorial

import lostcity.engine.script.Script.Companion.FINISHED
import lostcity.engine.script.ServerTriggerType
import nulled.GenericLocDistancedAction
import nulled.runescript.RuneScript
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
        on(ServerTriggerType.OPLOC1, "loc_3014") script@{ player: Player ->
            if (get(player, tutorial_progress) < runescape_guide_interact_with_scenery) {
                mesbox(player, "You need to talk to the 'Runescape Guide'|before you are allowed to proceed through this door.");
                return@script FINISHED
            }
            val outside = (player.position.x >= 3098)
            if (!outside) {
                GenericLocDistancedAction.start(player, player.loc.position) {
                    open_and_close_door(null, false, false).invoke(player)
                    if (get(player, tutorial_progress) == runescape_guide_interact_with_scenery) {
                        set(player, tutorial_progress, runescape_guide_interacted_with_door)
                        hint_stop(player)
                        tutorial_step_moving_around().invoke(player)
                    }
                }
            }
            FINISHED
        }
    }
}