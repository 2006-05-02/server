package nulled.scripts.chat

import nulled.runescript.RuneScript
import nulled.runescript.data.Interface.doubleobjbox
import nulled.runescript.data.Interface.doubleobjbox_com_0
import nulled.runescript.data.Interface.doubleobjbox_com_1
import nulled.runescript.data.Interface.doubleobjbox_com_2
import nulled.runescript.data.Interface.doubleobjbox_com_4
import nulled.runescript.data.Interface.doubleobjbox_com_5
import nulled.runescript.data.Interface.doubleobjbox_com_6
import org.apollo.game.model.World
import org.apollo.game.model.entity.Player
import org.apollo.game.plugin.RuneScriptContext

class chat(world: World, context: RuneScriptContext) : RuneScript(world, context) {
    companion object {
        fun doubleobjbox(player: Player, item1Id: Int, item2Id: Int, text: String) {
            println("doubleobj")
            split_init(text, -1, -1, -1)
            var page = 0
            while (page < splitPages) {
                doubleobjbox_page(player, item1Id, item2Id, 150, page)
                page++
            }
        }

        fun doubleobjbox_page(player: Player, item1Id: Int, item2Id: Int, scale: Int, page: Int) {
            val lines = split_linecount(page)
            println("doubleobjpage $lines")
            when (lines) {
                1 -> {
                    if_setobject(player, doubleobjbox_com_0, item1Id, scale);
                    if_settext(player, doubleobjbox_com_1, split_get(page, 0));
                    if_settext(player, doubleobjbox_com_2, "");
                    if_settext(player, doubleobjbox_com_4, "");
                    if_settext(player, doubleobjbox_com_5, "");
                    if_setobject(player, doubleobjbox_com_6, item2Id, scale);
                }
                2 -> {
                    if_setobject(player, doubleobjbox_com_0, item1Id, scale);
                    if_settext(player, doubleobjbox_com_1, split_get(page, 0));
                    if_settext(player, doubleobjbox_com_2, split_get(page, 1));
                    if_settext(player, doubleobjbox_com_4, "");
                    if_settext(player, doubleobjbox_com_5, "");
                    if_setobject(player, doubleobjbox_com_6, item2Id, scale);
                }
                3 -> {
                    if_setobject(player, doubleobjbox_com_0, item1Id, scale);
                    if_settext(player, doubleobjbox_com_1, split_get(page, 0));
                    if_settext(player, doubleobjbox_com_2, split_get(page, 1));
                    if_settext(player, doubleobjbox_com_4, split_get(page, 2));
                    if_settext(player, doubleobjbox_com_5, "");
                    if_setobject(player, doubleobjbox_com_6, item2Id, scale);
                }
                4 -> {
                    if_setobject(player, doubleobjbox_com_0, item1Id, scale);
                    if_settext(player, doubleobjbox_com_1, split_get(page, 1));
                    if_settext(player, doubleobjbox_com_2, split_get(page, 0));
                    if_settext(player, doubleobjbox_com_4, split_get(page, 2));
                    if_settext(player, doubleobjbox_com_5, split_get(page, 3));
                    if_setobject(player, doubleobjbox_com_6, item2Id, scale);
                }
            }
            if_openchat(player, doubleobjbox)
        }
    }
}