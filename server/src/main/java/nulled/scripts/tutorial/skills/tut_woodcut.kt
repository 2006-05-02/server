package nulled.scripts.tutorial.skills

import lostcity.engine.script.Script.Companion.ABORTED
import lostcity.engine.script.Script.Companion.FINISHED
import lostcity.engine.script.ServerTriggerType
import nulled.GenericLocDistancedAction
import nulled.GenericTimedAction
import nulled.runescript.RuneScript
import nulled.runescript.data.Animation.human_woodcutting_bronze_axe
import nulled.runescript.data.Items.logs
import nulled.runescript.data.Obj.bronze_axe
import nulled.runescript.data.Sounds.woodchop_1
import nulled.runescript.data.Sounds.woodchop_2
import nulled.runescript.data.Sounds.woodchop_3
import nulled.scripts.tutorial.data.TutorialConstants.survival_guide_build_fire
import nulled.scripts.tutorial.data.TutorialConstants.survival_guide_complete
import nulled.scripts.tutorial.data.TutorialConstants.survival_guide_cut_tree
import nulled.scripts.tutorial.data.TutorialConstants.tutorial_progress
import nulled.scripts.tutorial.tutorial.Companion.set_tutorial_progress
import nulled.scripts.tutorial.tutorial.Companion.tutorial_give_xp
import nulled.scripts.tutorial.tutorial.Companion.tutorial_please_wait_woodcutting
import nulled.scripts.tutorial.tutorial.Companion.tutorial_step_build_fire
import org.apollo.game.model.World
import org.apollo.game.model.entity.Player
import org.apollo.game.model.entity.Skill
import org.apollo.game.plugin.RuneScriptContext

class tut_woodcut(world: World, context: RuneScriptContext) : RuneScript(world, context) {
    init {
        on(ServerTriggerType.OPLOC1, "loc_3033", "loc_3034", "loc_3036") { player: Player ->
            GenericLocDistancedAction.start(player, player.loc.position) {
                tut_woodcutting().invoke(player)
            }
            FINISHED
        }
    }

    fun tut_woodcutting(): (Player) -> Int = script@{ player: Player ->
        if (get(player, tutorial_progress) < survival_guide_cut_tree) {
            mesbox(player,"You cannot cut down this tree yet.|You must progress further in the tutorial.");
            return@script ABORTED
        }

        if (inv_freespace(player.inventory) == 0) {
            anim(player, -1, 0)
            mesbox(player, "Your inventory is too full to hold any more logs.|To drop an item right-click on it and select drop.")
            return@script ABORTED
        }

        if (get(player, tutorial_progress) > survival_guide_complete) {
            mesbox(player, "Perhaps you've done enough woodcutting now.")
            return@script ABORTED
        }

        if (inv_total(logs, player.inventory) > 2) {
            mesbox(player, "Perhaps you've got enough logs for now.")
            return@script ABORTED
        }

        if (inv_total(bronze_axe, player.inventory) == 0) {
            mesbox(player, "You need an axe to chop this tree.|Talk to the survival expert to get one.")
            return@script ABORTED
        }

        player.action_delay = world.map_clock + 10
        tutorial_please_wait_woodcutting().invoke(player)
        anim(player, human_woodcutting_bronze_axe, 0)
        sound_synth(player, woodchop_1, 0, 18)
        GenericTimedAction.start(player, 2) {
            sound_synth(player, woodchop_1, 0, 0)
            GenericTimedAction.start(player, 1) {
                sound_synth(player, woodchop_1, 0, 8)
                GenericTimedAction.start(player, 1) {
                    anim(player, human_woodcutting_bronze_axe, 0)
                    sound_synth(player, woodchop_1, 0, 18)
                    GenericTimedAction.start(player, 2) {
                        sound_synth(player, woodchop_1, 0, 8)
                        GenericTimedAction.start(player, 1) {
                            sound_synth(player, woodchop_1, 0, 18)
                            GenericTimedAction.start(player, 1) {
                                anim(player, human_woodcutting_bronze_axe, 0)
                                sound_synth(player, woodchop_1, 0, 18)
                                anim(player, -1, 0)
                                tut_woodcutting_swap_loc().invoke(player)
                                tutorial_give_xp(Skill.WOODCUTTING, 25.0).invoke(player)
                                if (get(player, tutorial_progress) == survival_guide_cut_tree) {
                                    tut_advance_woodcutting().invoke(player)
                                } else {
                                    give_logs().invoke(player)
                                }
                            }
                        }
                    }
                }
            }
        }

        return@script FINISHED
    }

    fun tut_woodcutting_swap_loc(): (Player) -> Int =  script@{ player: Player ->
        val respawnRate = 16
        sound_synth(player, woodchop_3, 0, 18)
        loc_change(player, player.loc.nextStage, respawnRate)
        return@script FINISHED
    }

    fun tut_advance_woodcutting(): (Player) -> Int =  script@{ player: Player ->
        set(player, tutorial_progress, survival_guide_build_fire)
        hint_stop(player)
        inv_add(player.inventory, logs, 1)
        tutorial_step_build_fire().invoke(player)
        return@script FINISHED
    }

    fun give_logs(): (Player) -> Int =  script@{ player: Player ->
        inv_add(player.inventory, logs, 1);
        set_tutorial_progress().invoke(player)
        return@script FINISHED
    }
}