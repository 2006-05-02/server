package nulled.scripts.tutorial

import lostcity.engine.script.Script.Companion.FINISHED
import lostcity.engine.script.ServerTriggerType.OPNPC1
import nulled.runescript.RuneScript
import nulled.runescript.data.Animation.chat_default
import nulled.scripts.tutorial.data.TutorialConstants.runescape_guide
import nulled.scripts.tutorial.data.TutorialConstants.runescape_guide_designed_character
import nulled.scripts.tutorial.data.TutorialConstants.runescape_guide_interact_with_scenery
import nulled.scripts.tutorial.data.TutorialConstants.tutorial_progress
import nulled.scripts.tutorial.tutorial.Companion.set_tutorial_progress
import org.apollo.game.model.Direction
import org.apollo.game.model.World
import org.apollo.game.model.entity.Player
import org.apollo.game.plugin.RuneScriptContext
import org.apollo.plugins.spawn.spawnNpc

class runescape_guide(world: World, context: RuneScriptContext) : RuneScript(world, context){
    init {
        spawnNpc(runescape_guide, x = 3097, y = 3108, facing = Direction.WEST)

        on(OPNPC1, runescape_guide) { player: Player ->
            when (get(player, tutorial_progress)) {
                runescape_guide_designed_character -> runescape_guide_welcome().invoke(player)
                runescape_guide_interact_with_scenery -> runescape_guide_return().invoke(player)
                else -> runescape_guide_return().invoke(player)
            }
        }
    }

    fun runescape_guide_welcome(): (Player) -> Int = script@{ player: Player ->
        chatnpc(player, chat_default, "Greetings! I see you are a new arrival to this land. My|job is to welcome all the new visitors. So welcome!") {
            chatnpc(player, chat_default, "You have already learnt the first thing needed to|succeed in this world... Talking to other people!") {
                chatnpc(player, chat_default, "You will find many inhabitants of this world have useful|things to say to you. By clicking on them with your|mouse you can talk to them.") {
                    chatnpc(player, chat_default, "I would also suggest reading through some of the|supporting information on the website. There you can|find maps, a bestiary, and much more.") {
                        chatnpc(player, chat_default, "To continue the tutorial go through that door|over there, and speak to your first instructor!") {
                            set(player, tutorial_progress, runescape_guide_interact_with_scenery)
                            set_tutorial_progress().invoke(player)
                            if_close().invoke(player)
                        }
                    }
                }
            }
        }
        return@script FINISHED
    }

    fun runescape_guide_return(): (Player) -> Int = script@{ player: Player ->
        runescape_guide_welcome().invoke(player)
        return@script FINISHED
    }
}