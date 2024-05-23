package nulled.runescript

import CloseDialogueListener
import lostcity.engine.script.ServerTriggerType
import nulled.runescript.data.Interface.doubleobjbox
import nulled.runescript.data.Interface.doubleobjbox_com_0
import nulled.runescript.data.Interface.doubleobjbox_com_1
import nulled.runescript.data.Interface.doubleobjbox_com_2
import nulled.runescript.data.Interface.doubleobjbox_com_4
import nulled.runescript.data.Interface.doubleobjbox_com_5
import nulled.runescript.data.Interface.doubleobjbox_com_6
import nulled.runescript.data.Interface.message1
import nulled.runescript.data.Interface.message1_com0
import nulled.runescript.data.Interface.message2
import nulled.runescript.data.Interface.message2_com0
import nulled.runescript.data.Interface.message2_com1
import nulled.runescript.data.Interface.message3
import nulled.runescript.data.Interface.message3_com0
import nulled.runescript.data.Interface.message3_com1
import nulled.runescript.data.Interface.message3_com2
import nulled.runescript.data.Interface.message4
import nulled.runescript.data.Interface.message4_com0
import nulled.runescript.data.Interface.message4_com1
import nulled.runescript.data.Interface.message4_com2
import nulled.runescript.data.Interface.message4_com3
import nulled.runescript.data.Interface.message5
import nulled.runescript.data.Interface.message5_com0
import nulled.runescript.data.Interface.message5_com1
import nulled.runescript.data.Interface.message5_com2
import nulled.runescript.data.Interface.message5_com3
import nulled.runescript.data.Interface.message5_com4
import org.apollo.game.message.impl.*
import org.apollo.game.model.Animation
import org.apollo.game.model.Position
import org.apollo.game.model.World
import org.apollo.game.model.entity.Player
import org.apollo.game.model.entity.attr.NumericalAttribute
import org.apollo.game.model.inter.dialogue.DialogueListener
import org.apollo.game.model.inv.Inventory
import org.apollo.game.plugin.RuneScriptContext
import org.apollo.game.plugin.api.replaceObject
import org.apollo.plugins.api.ChatEmotes
import org.apollo.plugins.api.dialogue.ChatNpcAction
import kotlin.script.experimental.annotations.KotlinScript

@KotlinScript(displayName = "RuneScript",
    fileExtension = "rs2",
    compilationConfiguration = RuneScriptDefinition::class
)
abstract class RuneScript(var world: World, var context: RuneScriptContext) {

    fun on(triggerType: ServerTriggerType, vararg subjects: String, handler: (Player) -> Int?) {
        for (subject in subjects) {
            registrations += 1
            on(triggerType, subject, handler)
        }
    }

    fun on(triggerType: ServerTriggerType, subject: String, handler: (Player) -> Int?) {
        registrations += 1
        EventRegistry.registerEvent(triggerType, subject, handler)
    }

    fun on(triggerType: ServerTriggerType, subject: Int, handler: (Player) -> Int?) {
        registrations += 1
        EventRegistry.registerEvent(triggerType, subject.toString(), handler)
    }

    companion object {
        var registrations = 0
        const val GLOBAL = "_"

        var splitPages = -1
        var page0 = ""
        var page1 = ""
        var page2 = ""
        var page3 = ""
        var page4 = ""

        fun mes(player: Player, mes: String) {
            player.send(ServerChatMessage(mes))
        }

        fun if_settab(player: Player, com: Int?, tab: Int) {
            player.send(SwitchTabInterfaceMessage(tab, com ?: -1))
        }

        fun if_settext(player: Player, widget: Int, text: String) {
            player.send(SetWidgetTextMessage(widget, text))
        }

        fun if_openchatsticky(player: Player, com: Int) {
            player.send(OpenTutorialDialogueInterfaceMessage(com))
        }

        fun split_init(text: String, maxLen: Int, fontId: Int, mesanimId: Int) {
            var count = 0
            for (page in text.split("/")) {
                when (count) {
                    0 -> page0 = page
                    1 -> page1 = page
                    2 -> page2 = page
                    3 -> page3 = page
                    4 -> page4 = page
                }
                count++
            }
            splitPages = count
        }

        fun split_linecount(page: Int): Int {
            return when (page) {
                0 -> page0.split("|").size
                1 -> page1.split("|").size
                2 -> page2.split("|").size
                3 -> page3.split("|").size
                4 -> page4.split("|").size
                else -> throw RuneScriptException("split_linecount invalid page")
            }
        }

        fun split_get(page: Int, line: Int): String {
            return when (page) {
                0 -> page0.split("|")[line]
                1 -> page1.split("|")[line]
                2 -> page2.split("|")[line]
                3 -> page3.split("|")[line]
                4 -> page4.split("|")[line]
                else -> throw RuneScriptException("split_get invalid page")
            }
        }

        fun chatnpc(player: Player, emote: Int, text: String, continueAction: ((Player) -> Unit)? = null) {
            var listener: DialogueListener? = null
            if (continueAction != null) {
                listener = object : DialogueListener {
                    override fun interfaceClosed() {

                    }

                    override fun buttonClicked(button: Int): Boolean {
                        return true
                    }

                    override fun continued(player: Player) {
                        continueAction.invoke(player)
                    }
                }
            } else {
                listener = CloseDialogueListener
            }
            ChatNpcAction.start(player, player.npc.position) {
                val lines = text.split("|").size
                when (lines) {
                    1 -> player.sendNpcDialogue(player.npc, ChatEmotes.of(emote), text, listener)
                    2 -> {
                        val l1 = text.split("|")[0]
                        val l2 = text.split("|")[1]
                        player.sendNpc2Dialogue(player.npc, ChatEmotes.of(emote), l1, l2, listener)
                    }
                    3 -> {
                        val l1 = text.split("|")[0]
                        val l2 = text.split("|")[1]
                        val l3 = text.split("|")[2]
                        player.sendNpc3Dialogue(player.npc, ChatEmotes.of(emote), l1, l2, l3, listener)
                    }
                    4 -> {
                        val l1 = text.split("|")[0]
                        val l2 = text.split("|")[1]
                        val l3 = text.split("|")[2]
                        val l4 = text.split("|")[3]
                        player.sendNpc4Dialogue(player.npc, ChatEmotes.of(emote), l1, l2, l3, l4, listener)
                    }
                }
            }
        }

        fun hint_npc(uid: Int) : (Player) -> Unit = { player: Player ->
            val npc = player.world.npcRepository.get(uid) ?: throw RuneScriptException("hint_npc invalid npc uid")
            player.send(MobHintIconMessage.create(npc))
        }

        fun hint_coord(player: Player, type: HintIconMessage.Type, position: Position, height: Int) {
            player.send(PositionHintIconMessage(type, position, height))
        }

        fun if_close() : (Player) -> Unit = { player: Player ->
            if (player.interfaceSet.canClose)
                player.interfaceSet.close()
        }

        fun mesbox(player: Player, message: String) {
            split_init(message, -1, -1, -1)
            var page = 0
            while (page < splitPages) {
                mesbox_page(player, page)
                page += 1
            }
        }

        fun mesbox_page(player: Player, page: Int) {
            val lines = split_linecount(page)
            when (lines) {
                1 -> {
                    if_settext(player, message1_com0, split_get(page, 0))
                    if_openchat(player, message1)
                }
                2 -> {
                    if_settext(player, message2_com0, split_get(page, 0))
                    if_settext(player, message2_com1, split_get(page, 1))
                    if_openchat(player, message2)
                }
                3 -> {
                    if_settext(player, message3_com0, split_get(page, 0))
                    if_settext(player, message3_com1, split_get(page, 1))
                    if_settext(player, message3_com2, split_get(page, 2))
                    if_openchat(player, message3)
                }
                4 -> {
                    if_settext(player, message4_com0, split_get(page, 0))
                    if_settext(player, message4_com1, split_get(page, 1))
                    if_settext(player, message4_com2, split_get(page, 2))
                    if_settext(player, message4_com3, split_get(page, 3))
                    if_openchat(player, message4)
                }
                5 -> {
                    if_settext(player, message5_com0, split_get(page, 0))
                    if_settext(player, message5_com1, split_get(page, 1))
                    if_settext(player, message5_com2, split_get(page, 2))
                    if_settext(player, message5_com3, split_get(page, 3))
                    if_settext(player, message5_com4, split_get(page, 4))
                    if_openchat(player, message5)
                }
            }
        }

        fun if_openchat(player: Player, com: Int) {
            player.interfaceSet.openDialogue(com)
        }

        fun p_teleport(destination: Position): (Player) -> Unit = { player ->
            player.teleport(destination)
        }

        fun sound_synth(player: Player, id: Int, loops: Int, delay: Int) {
            player.send(PlaySoundMessage(id, loops, delay))
        }

        fun if_openmainmodal(player: Player, com: Int) {
            player.interfaceSet.openWindow(com)
        }

        fun allowdesign(player: Player, allowed: Boolean) {
            player.allowdesign = allowed
        }

        fun if_setobject(player: Player, widget: Int, itemID: Int, scale: Int) {
            player.send(SetWidgetItemModelMessage(widget, itemID, scale))
        }

        fun doubleobjbox(player: Player, item1ID : Int, item2ID: Int, scale: Int, page: Int, text: String, continueAction: ((Player) -> Unit)? = null) {
            split_init(text, -1, -1, -1)
            var listener: DialogueListener?
            if (continueAction != null) {
                listener = object : DialogueListener {
                    override fun interfaceClosed() {

                    }

                    override fun buttonClicked(button: Int): Boolean {
                        return true
                    }

                    override fun continued(player: Player) {
                        continueAction.invoke(player)
                    }
                }
            } else {
                listener = CloseDialogueListener
            }
            ChatNpcAction.start(player, player.npc.position) {
                val lines = text.split("|").size
                when (lines) {
                    1 -> {
                        if_setobject(player, doubleobjbox_com_0, item1ID, scale);
                        if_settext(player, doubleobjbox_com_1, split_get(page, 0));
                        if_settext(player, doubleobjbox_com_2, "");
                        if_settext(player, doubleobjbox_com_4, "");
                        if_settext(player, doubleobjbox_com_5, "");
                        if_setobject(player, doubleobjbox_com_6, item2ID, scale);
                    }
                    2 -> {
                        if_setobject(player, doubleobjbox_com_0, item1ID, scale);
                        if_settext(player, doubleobjbox_com_1, split_get(page, 0));
                        if_settext(player, doubleobjbox_com_2, split_get(page, 1));
                        if_settext(player, doubleobjbox_com_4, "");
                        if_settext(player, doubleobjbox_com_5, "");
                        if_setobject(player, doubleobjbox_com_6, item2ID, scale);
                    }
                    3 -> {
                        if_setobject(player, doubleobjbox_com_0, item1ID, scale);
                        if_settext(player, doubleobjbox_com_1, split_get(page, 0));
                        if_settext(player, doubleobjbox_com_2, split_get(page, 1));
                        if_settext(player, doubleobjbox_com_4, split_get(page, 2));
                        if_settext(player, doubleobjbox_com_5, "");
                        if_setobject(player, doubleobjbox_com_6, item2ID, scale);
                    }
                    4 -> {
                        if_setobject(player, doubleobjbox_com_0, item1ID, scale);
                        if_settext(player, doubleobjbox_com_1, split_get(page, 1));
                        if_settext(player, doubleobjbox_com_2, split_get(page, 0));
                        if_settext(player, doubleobjbox_com_4, split_get(page, 2));
                        if_settext(player, doubleobjbox_com_5, split_get(page, 3));
                        if_setobject(player, doubleobjbox_com_6, item2ID, scale);
                    }
                }
                player.interfaceSet.openDialogue(listener, doubleobjbox)
            }
        }

        fun if_settabflash(player: Player, widget: Int) {
            player.send(FlashTabInterfaceMessage(widget))
        }

        fun inv_add(inventory: Inventory, itemID: Int, count: Int, onlyIfMissing: Boolean = false) {
            if (onlyIfMissing) {
                if (!inventory.contains(itemID))
                    inventory.add(itemID, count)
            } else {
                inventory.add(itemID, count)
            }
        }

        fun hint_stop(player: Player) {
            player.send(PositionHintIconMessage.reset())
        }

        fun inv_freespace(inventory: Inventory) : Int {
            return inventory.freeSlots()
        }

        fun anim(player: Player, id: Int, delay: Int) {
            player.playAnimation(Animation(id, delay))
        }

        fun inv_total(itemID: Int, vararg inventories: Inventory) : Int {
            var count = 0
            for (inventory in inventories)
                count += inventory.items.filterNotNull().count { it.id == itemID }
            return count
        }

        fun loc_change(player: Player, newObjectID: Int, delay: Int) {
            player.world.replaceObject(player.loc, newObjectID, delay)
        }

        fun givexp(player: Player, skill: Int, xp: Double) {
            player.skillSet.addExperience(skill, xp)
        }

        fun get(player: Player, attribute: String) : Int {
            if (!player.attributes.containsKey(attribute))
                player.setAttribute(attribute, NumericalAttribute(0L))
            return (player.getAttribute(attribute).value as Long).toInt()
        }

        private fun set(player: Player, attribute: String, value: Long) {
            player.setAttribute(attribute, NumericalAttribute(value))
        }

        fun set(player: Player, attribute: String, value: Int) {
            set(player, attribute, value.toLong())
        }
    }
}

