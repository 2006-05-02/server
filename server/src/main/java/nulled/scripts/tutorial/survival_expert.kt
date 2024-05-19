package nulled.scripts.tutorial

import lostcity.engine.script.Script.Companion.FINISHED
import lostcity.engine.script.ServerTriggerType.OPNPC1
import lostcity.engine.script.ServerTriggerType.TUTORIAL_CLICKSIDE
import nulled.runescript.RuneScript
import nulled.runescript.data.Animation.chat_default
import nulled.runescript.data.Obj.bronze_axe
import nulled.runescript.data.Obj.tinderbox
import nulled.scripts.tutorial.data.TutorialConstants.survival_expert
import nulled.scripts.tutorial.data.TutorialConstants.runescape_guide_interact_with_scenery
import nulled.scripts.tutorial.data.TutorialConstants.runescape_guide_interacted_with_door
import nulled.scripts.tutorial.data.TutorialConstants.survival_guide_build_fire
import nulled.scripts.tutorial.data.TutorialConstants.survival_guide_cut_tree
import nulled.scripts.tutorial.data.TutorialConstants.survival_guide_open_inventory
import nulled.scripts.tutorial.data.TutorialConstants.tutorial_progress
import nulled.scripts.tutorial.tutorial.Companion.set_tutorial_progress
import nulled.scripts.tutorial.tutorial.Companion.tutorial_step_view_inventory
import org.apollo.game.model.Direction
import org.apollo.game.model.World
import org.apollo.game.model.entity.Player
import org.apollo.game.plugin.RuneScriptContext
import org.apollo.plugins.spawn.spawnNpc

class survival_expert(world: World, context: RuneScriptContext) : RuneScript(world, context){
    init {
        spawnNpc(survival_expert, x = 3104, y = 3095, facing = Direction.NORTH)

        on(OPNPC1, survival_expert) { player: Player ->
            when (get(player, tutorial_progress)) {
                runescape_guide_interacted_with_door -> survival_guide_start().invoke(player)
                survival_guide_open_inventory -> survival_guide_look_at_menu().invoke(player)
                survival_guide_cut_tree, survival_guide_build_fire -> survival_guide_fire().invoke(player)
                /*survival_guide_open_skill_menu -> survival_guide_look_at_menu;
                survival_guide_opened_skill_menu -> survival_guide_fishing;
                survival_guide_fish_shrimps,
                survival_guide_cook_shrimps,
                survival_guide_burnt_shrimps, -> survival_guide_fishing_after_burning;*/
                else -> survival_guide_start().invoke(player)
            }
            FINISHED
        }

        on(TUTORIAL_CLICKSIDE, GLOBAL) { player: Player ->
            if (get(player, tutorial_progress) == survival_guide_open_inventory) {
                inv_add(player.inventory, tinderbox, 1, onlyIfMissing = true)
                inv_add(player.inventory, bronze_axe, 1, onlyIfMissing = true)
                set(player, tutorial_progress, survival_guide_cut_tree)
                set_tutorial_progress().invoke(player)
            }
            FINISHED
        }
    }

    fun survival_guide_fire(): (Player) -> Int = { player: Player ->
        FINISHED
    }

    fun survival_guide_look_at_menu(): (Player) -> Int = { player: Player ->
        chatnpc(player, chat_default, "Hello again. You should take a look at that menu before we continue.")
        FINISHED
    }

    fun survival_guide_start(): (Player) -> Int = { player: Player ->
        chatnpc(player, chat_default, "Hello there newcomer. My name is Brynna. My job is|to teach you a few survival tips and tricks. First off|we're going to start with the most basic survival skill of|all; making a fire.") {
            doubleobjbox(player, tinderbox, bronze_axe, 150, 0, "The Survival Guide gives you a |@blu@Tinderbox@bla@ |and a |@blu@Bronze Hatchet!") {
                set(player, tutorial_progress, survival_guide_open_inventory)
                tutorial_step_view_inventory().invoke(player)
                if_close().invoke(player)
            }
        }
        FINISHED
    }
}