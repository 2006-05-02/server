package nulled.scripts.tutorial

import nulled.runescript.RuneScript
import nulled.runescript.data.Interface.tutorial_text
import nulled.runescript.data.Interface.tutorial_text_line1
import nulled.runescript.data.Interface.tutorial_text_line2
import nulled.runescript.data.Interface.tutorial_text_line3
import nulled.runescript.data.Interface.tutorial_text_line4
import nulled.runescript.data.Interface.tutorial_text_title
import org.apollo.game.model.World
import org.apollo.game.model.entity.Player
import org.apollo.game.plugin.RuneScriptContext

class tutorial_steps(world: World, context: RuneScriptContext) : RuneScript(world, context) {
    companion object {
        fun tutorialstep(player: Player, title: String, text: String) {
            if_settext(player, tutorial_text_title, title)
            split_init(text, -1, -1, -1)
            var page = 0
            while (page < splitPages) {
                tutorialstep_page(player, page)
                page += 1
            }
        }

        fun tutorialstep_page(player: Player, page: Int) {
            val lines = split_linecount(page)
            when (lines) {
                1 -> {
                    if_settext(player, tutorial_text_line1, split_get(page, 0))
                    if_settext(player, tutorial_text_line2, "");
                    if_settext(player, tutorial_text_line3, "");
                    if_settext(player, tutorial_text_line4, "");
                }
                2 -> {
                    if_settext(player, tutorial_text_line1, split_get(page, 0))
                    if_settext(player, tutorial_text_line2, split_get(page, 1))
                    if_settext(player, tutorial_text_line3, "");
                    if_settext(player, tutorial_text_line4, "");
                }
                3 -> {
                    if_settext(player, tutorial_text_line1, split_get(page, 0))
                    if_settext(player, tutorial_text_line2, split_get(page, 1))
                    if_settext(player, tutorial_text_line3, split_get(page, 2))
                    if_settext(player, tutorial_text_line4, "");
                }
                4 -> {
                    if_settext(player, tutorial_text_line1, split_get(page, 0))
                    if_settext(player, tutorial_text_line2, split_get(page, 1))
                    if_settext(player, tutorial_text_line3, split_get(page, 2))
                    if_settext(player, tutorial_text_line4, split_get(page, 3))
                }
            }
            if_openchatsticky(player, tutorial_text)
        }
    }
}