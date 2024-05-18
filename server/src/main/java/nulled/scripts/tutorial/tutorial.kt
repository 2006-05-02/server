package nulled.scripts.tutorial

import lostcity.engine.script.Script.Companion.FINISHED
import lostcity.engine.script.ServerTriggerType
import nulled.runescript.RuneScript
import nulled.runescript.RuneScriptException
import nulled.runescript.data.Interface.logout
import nulled.runescript.data.Interface.options
import nulled.runescript.data.Interface.player_kit
import nulled.runescript.data.Interface.player_kit_accept
import nulled.runescript.data.Tab.tab_combat_options
import nulled.runescript.data.Tab.tab_friends
import nulled.runescript.data.Tab.tab_game_options
import nulled.runescript.data.Tab.tab_ignore
import nulled.runescript.data.Tab.tab_inventory
import nulled.runescript.data.Tab.tab_logout
import nulled.runescript.data.Tab.tab_magic
import nulled.runescript.data.Tab.tab_musicplayer
import nulled.runescript.data.Tab.tab_player_controls
import nulled.runescript.data.Tab.tab_prayer
import nulled.runescript.data.Tab.tab_quest_journal
import nulled.runescript.data.Tab.tab_skills
import nulled.runescript.data.Tab.tab_wornitems
import nulled.scripts.tutorial.data.TutorialConstants.runescape_guide_designed_character
import nulled.scripts.tutorial.data.TutorialConstants.runescape_guide_interact_with_scenery
import nulled.scripts.tutorial.data.TutorialConstants.runescape_guide_start
import nulled.scripts.tutorial.data.TutorialConstants.tutorial_progress
import nulled.scripts.tutorial.tutorial_steps.Companion.tutorialstep
import org.apollo.game.message.impl.HintIconMessage
import org.apollo.game.model.Position
import org.apollo.game.model.World
import org.apollo.game.model.entity.Player
import org.apollo.game.plugin.RuneScriptContext
import org.apollo.plugins.spawn.npcs.RunescapeGuide
import org.apollo.plugins.spawn.npcs.SurvivalExpert

class tutorial(world: World, context: RuneScriptContext) : RuneScript(world, context) {
    init {
        on(ServerTriggerType.IF_BUTTON, player_kit_accept) { player: Player ->
            player.interfaceSet.canClose = true
            if_close().invoke(player)
            FINISHED
        }
        on(ServerTriggerType.IF_CLOSE, player_kit) { player: Player ->
            set(player, tutorial_progress, runescape_guide_designed_character)
            allowdesign(player, false)
            FINISHED
        }
    }
    companion object {
        fun tutorial_step_getting_started() : (Player) -> Int = { player: Player ->
            set_hint_runescape_guide().invoke(player)
            tutorialstep(player, "Getting started", "To start the tutorial use your left mouse-button to click on the|'RuneScape Guide' in this room. He is indicated by a flashing|yellow arrow above his head. If you can't see him, use your|keyboard's arrow keys to rotate the view.")
            FINISHED
        }

        fun tutorial_step_interact_with_scenery() : (Player) -> Int = { player: Player ->
            hint_coord(player, HintIconMessage.Type.WEST, Position(3098, 3107), 128)
            tutorialstep(player, "Interacting with scenery", "You can interact with many items of scenery by simply clicking|on them. Right clicking will also give more options. Try it|with the things in this room, then click on the door indicated|with the yellow arrow to go through to the next instructor.")
            FINISHED
        }

        fun tutorial_step_moving_around() : (Player) -> Int = { player: Player ->
            tutorialstep(player, "Moving around", "Follow the path to find the next instructor. Clicking on the|ground will walk you to that point. Talk to the survival expert by|the pond to continue the tutorial. Remember you can rotate|the view by pressing the arrow keys.")
            set_hint_icon_survival_guide().invoke(player)
            FINISHED
        }

        private fun set_hint_runescape_guide() : (Player) -> Int = { player: Player ->
            if (get(player, tutorial_progress) < runescape_guide_interact_with_scenery) {
                hint_npc(RunescapeGuide.INSTANCE.uid).invoke(player)
            }
            FINISHED
        }

        private fun set_hint_icon_survival_guide() : (Player) -> Int = { player: Player ->
            if (get(player, tutorial_progress) < runescape_guide_interact_with_scenery) {
                hint_npc(SurvivalExpert.INSTANCE.uid).invoke(player)
            }
            FINISHED
        }

        fun set_tutorial_progress() : (Player) -> Int = { player: Player ->
            when (get(player, tutorial_progress)) {
                runescape_guide_start,
                runescape_guide_designed_character -> tutorial_step_getting_started().invoke(player)
                runescape_guide_interact_with_scenery -> tutorial_step_interact_with_scenery().invoke(player)
                else -> throw RuneScriptException("invalid tutorial progress")
            }
        }

        fun start_tutorial() : (Player) -> Int = { player: Player ->
            // logging into a new character might show old tabs so we're going to clear them
            if_settab(player, null, tab_combat_options);
            if_settab(player, null, tab_skills);
            if_settab(player, null, tab_quest_journal);
            if_settab(player, null, tab_inventory);
            if_settab(player, null, tab_wornitems);
            if_settab(player, null, tab_prayer);
            if_settab(player, null, tab_magic);
            if_settab(player, null, tab_friends);
            if_settab(player, null, tab_ignore);
            if_settab(player, logout, tab_logout);
            if_settab(player, options, tab_game_options);
            if_settab(player, null, tab_player_controls);
            if_settab(player, null, tab_musicplayer);
            tutorial_set_active_tabs().invoke(player)
            set_tutorial_progress().invoke(player)

            if (get(player, tutorial_progress) == runescape_guide_start) {
                if_close().invoke(player)
                if_openmainmodal(player, player_kit)
                allowdesign(player, true)
            }
            FINISHED
        }

        fun tutorial_set_active_tabs() : (Player) -> Int =  { player: Player ->
            if (get(player, tutorial_progress) > runescape_guide_start) {
                if_settab(player, options, tab_game_options);
            }
            FINISHED
        }
    }
}