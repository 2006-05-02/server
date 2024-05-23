package nulled.cache.tools

import nulled.cache.decoder.ObjDefinitionDecoder
import nulled.cache.def.ObjDefinition
import nulled.cache.def.ObjDefinition.Companion.count
import nulled.cache.def.ObjDefinition.Companion.lookup
import nulled.cache.fs.IndexedFileSystem
import org.apollo.util.tools.EquipmentConstants
import java.io.BufferedOutputStream
import java.io.DataOutputStream
import java.io.FileOutputStream
import java.nio.file.Paths

object EquipmentUpdater {
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val release = "377"

        DataOutputStream(BufferedOutputStream(FileOutputStream("data/equipment-$release.dat"))).use { os ->
            IndexedFileSystem(
                Paths.get("data/fs", release), true
            ).use { fs ->
                val decoder = ObjDefinitionDecoder(fs)
                decoder.run()

                val count = count()
                os.writeShort(count)
                for (id in 0 until count) {
                    val definition = lookup(id)
                    val type = getWeaponType(definition!!)
                    os.writeByte(type)

                    if (type != -1) {
                        os.writeBoolean(isTwoHanded(definition))
                        os.writeBoolean(isFullBody(definition))
                        os.writeBoolean(isFullHat(definition))
                        os.writeBoolean(isFullMask(definition))
                        os.writeByte(getAttackRequirement(definition))
                        os.writeByte(getStrengthRequirement(definition))
                        os.writeByte(getDefenceRequirement(definition))
                        os.writeByte(getRangedRequirement(definition))
                        os.writeByte(getPrayerRequirement(definition))
                        os.writeByte(getMagicRequirement(definition))
                    }
                }
            }
        }
    }

    private fun getAttackRequirement(definition: ObjDefinition): Int {
        var name = definition.name
        if (name == null) {
            name = "null"
        }

        if (name == "Black sword") {
            return 10
        } else if (name == "Black dagger") {
            return 10
        } else if (name == "Black spear") {
            return 10
        } else if (name == "Black longsword") {
            return 10
        } else if (name == "Black scimitar") {
            return 10
        } else if (name == "Black axe") {
            return 10
        } else if (name == "Black battleaxe") {
            return 10
        } else if (name == "Black mace") {
            return 10
        } else if (name == "Black halberd") {
            return 10
        } else if (name == "Mithril sword") {
            return 20
        } else if (name == "Mithril dagger") {
            return 20
        } else if (name == "Mithril spear") {
            return 20
        } else if (name == "Mihril longsword") {
            return 20
        } else if (name == "Mithril scimitar") {
            return 20
        } else if (name == "Mithril axe") {
            return 20
        } else if (name == "Mithril battleaxe") {
            return 20
        } else if (name == "Mithril mace") {
            return 20
        } else if (name == "Mithril halberd") {
            return 20
        } else if (name == "Adamant sword") {
            return 30
        } else if (name == "Adamant dagger") {
            return 30
        } else if (name == "Adamant spear") {
            return 30
        } else if (name == "Adamant longsword") {
            return 30
        } else if (name == "Adamant scimitar") {
            return 30
        } else if (name == "Adamant axe") {
            return 30
        } else if (name == "Adamant battleaxe") {
            return 30
        } else if (name == "Adamant mace") {
            return 30
        } else if (name == "Adamant halberd") {
            return 30
        } else if (name == "Rune sword") {
            return 40
        } else if (name == "Rune dagger") {
            return 40
        } else if (name == "Rune spear") {
            return 40
        } else if (name == "Rune longsword") {
            return 40
        } else if (name == "Rune scimitar") {
            return 40
        } else if (name == "Rune axe") {
            return 40
        } else if (name == "Rune battleaxe") {
            return 40
        } else if (name == "Rune mace") {
            return 40
        } else if (name == "Rune halberd") {
            return 40
        } else if (name == "Dragon sword") {
            return 60
        } else if (name == "Dragon dagger(s)") {
            return 60
        } else if (name == "Dragon dagger") {
            return 60
        } else if (name.startsWith("Dragon spear")) {
            return 60
        } else if (name == "Dragon longsword") {
            return 60
        } else if (name == "Dragon scimitar") {
            return 60
        } else if (name == "Dragon axe") {
            return 60
        } else if (name == "Dragon battleaxe") {
            return 60
        } else if (name == "Dragon mace") {
            return 60
        } else if (name == "Dragon halberd") {
            return 60
        } else if (name == "Abyssal whip") {
            return 70
        } else if (name == "Veracs flail") {
            return 70
        } else if (name == "Torags hammers") {
            return 70
        } else if (name == "Dharoks greataxe") {
            return 70
        } else if (name == "Guthans warspear") {
            return 70
        } else if (name == "Ahrims staff") {
            return 70
        } else if (name == "Granite maul") {
            return 50
        } else if (name == "Toktz-xil-ak") {
            return 60
        } else if (name == "Tzhaar-ket-em") {
            return 60
        } else if (name == "Toktz-xil-ek") {
            return 60
        } else if (name == "Granite legs") {
            return 99
        } else if (name == "Mud staff") {
            return 30
        } else if (name == "Armadyl godsword") {
            return 75
        } else if (name == "Bandos godsword") {
            return 75
        } else if (name == "Saradomin godsword") {
            return 75
        } else if (name == "Zamorak godsword") {
            return 75
        } else if (name == "Lava battlestaff") {
            return 30
        } else if (name == "Toktz-mej-tal") {
            return 60
        } else if (name == "Ancient staff") {
            return 50
        }

        return 1
    }

    private fun getDefenceRequirement(definition: ObjDefinition): Int {
        val id = definition.id
        var name = definition.name
        if (name == null) {
            name = "null"
        }

        if (name == "Rune boots") {
            return 40
        } else if (id == 2499) {
            return 40
        } else if (id == 4123) {
            return 5
        } else if (id == 4125) {
            return 10
        } else if (id == 4127) {
            return 20
        } else if (id == 4129) {
            return 30
        } else if (id == 7990) {
            return 60
        } else if (id == 2501) {
            return 40
        } else if (id == 1131) {
            return 10
        } else if (id == 2503) {
            return 40
        } else if (id == 1135) {
            return 40
        } else if (id == 7462) {
            return 42
        } else if (id == 7461) {
            return 42
        } else if (id == 7460) {
            return 42
        } else if (id == 7459) {
            return 20
        } else if (id == 7458) {
            return 1
        } else if (id == 7457) {
            return 1
        } else if (id == 7456) {
            return 1
        } else if (id == 2661) {
            return 40
        } else if (id == 2667) {
            return 40
        } else if (id == 3479) {
            return 40
        } else if (name == "White med helm") {
            return 10
        } else if (name == "White chainbody") {
            return 10
        } else if (name.startsWith("White full helm")) {
            return 10
        } else if (name.startsWith("White platebody")) {
            return 10
        } else if (name.startsWith("White plateskirt")) {
            return 10
        } else if (name.startsWith("White platelegs")) {
            return 10
        } else if (name.startsWith("White kiteshield")) {
            return 10
        } else if (name.startsWith("White sq shield")) {
            return 10
        } else if (name.startsWith("Studded chaps")) {
            return 1
        } else if (name.startsWith("Studded")) {
            return 20
        } else if (name.startsWith("Black kiteshield(h)")) {
            return 10
        } else if (name.startsWith("Rune kiteshield(h)")) {
            return 40
        } else if (name == "Black med helm") {
            return 10
        } else if (name == "Black chainbody") {
            return 10
        } else if (name.startsWith("Black full helm")) {
            return 10
        } else if (name.startsWith("Black platebody")) {
            return 10
        } else if (name.startsWith("Black plateskirt")) {
            return 10
        } else if (name.startsWith("Black platelegs")) {
            return 10
        } else if (name.startsWith("Black kiteshield")) {
            return 10
        } else if (name.startsWith("Black sq shield")) {
            return 10
        } else if (name == "Mithril med helm") {
            return 20
        } else if (name == "Mithril chainbody") {
            return 20
        } else if (name.startsWith("Mithril full helm")) {
            return 20
        } else if (name.startsWith("Mithril platebody")) {
            return 20
        } else if (name.startsWith("Mithril plateskirt")) {
            return 20
        } else if (name.startsWith("Mithril platelegs")) {
            return 20
        } else if (name.startsWith("Mithril kiteshield")) {
            return 20
        } else if (name.startsWith("Mithril sq shield")) {
            return 20
        } else if (name == "Adamant med helm") {
            return 30
        } else if (name == "Adamant chainbody") {
            return 30
        } else if (name.startsWith("Adamant full helm")) {
            return 30
        } else if (name.startsWith("Adamant platebody")) {
            return 30
        } else if (name.startsWith("Adamant plateskirt")) {
            return 30
        } else if (name.startsWith("Adamant platelegs")) {
            return 30
        } else if (name.startsWith("Adamant kiteshield")) {
            return 30
        } else if (name.startsWith("Adamant sq shield")) {
            return 30
        } else if (name.startsWith("Adam full helm")) {
            return 30
        } else if (name.startsWith("Adam platebody")) {
            return 30
        } else if (name.startsWith("Adam plateskirt")) {
            return 30
        } else if (name.startsWith("Adam platelegs")) {
            return 30
        } else if (name.startsWith("Adam kiteshield")) {
            return 30
        } else if (name.startsWith("Adam kiteshield(h)")) {
            return 30
        } else if (name.startsWith("D-hide body(g)")) {
            return 40
        } else if (name.startsWith("D-hide body(t)")) {
            return 40
        } else if (name == "Dragon sq shield") {
            return 60
        } else if (name == "Dragon med helm") {
            return 60
        } else if (name == "Dragon chainbody") {
            return 60
        } else if (name == "Dragon plateskirt") {
            return 60
        } else if (name == "Dragon platelegs") {
            return 60
        } else if (name == "Dragon sq shield") {
            return 60
        } else if (name == "Rune med helm") {
            return 40
        } else if (name == "Rune chainbody") {
            return 40
        } else if (name.startsWith("Rune full helm")) {
            return 40
        } else if (name.startsWith("Rune platebody")) {
            return 40
        } else if (name.startsWith("Rune plateskirt")) {
            return 40
        } else if (name.startsWith("Rune platelegs")) {
            return 40
        } else if (name.startsWith("Rune kiteshield")) {
            return 40
        } else if (name.startsWith("Zamorak full helm")) {
            return 40
        } else if (name.startsWith("Zamorak platebody")) {
            return 40
        } else if (name.startsWith("Zamorak plateskirt")) {
            return 40
        } else if (name.startsWith("Zamorak platelegs")) {
            return 40
        } else if (name.startsWith("Zamorak kiteshield")) {
            return 40
        } else if (name.startsWith("Guthix full helm")) {
            return 40
        } else if (name.startsWith("Guthix platebody")) {
            return 40
        } else if (name.startsWith("Guthix plateskirt")) {
            return 40
        } else if (name.startsWith("Guthix platelegs")) {
            return 40
        } else if (name.startsWith("Guthix kiteshield")) {
            return 40
        } else if (name.startsWith("Saradomin full")) {
            return 40
        } else if (name.startsWith("Saradomrangedin plate")) {
            return 40
        } else if (name.startsWith("Saradomin plateskirt")) {
            return 40
        } else if (name.startsWith("Saradomin legs")) {
            return 40
        } else if (name.startsWith("Zamorak kiteshield")) {
            return 40
        } else if (name.startsWith("Rune sq shield")) {
            return 40
        } else if (name == "Gilded full helm") {
            return 40
        } else if (name == "Gilded platebody") {
            return 40
        } else if (name == "Gilded plateskirt") {
            return 40
        } else if (name == "Gilded platelegs") {
            return 40
        } else if (name == "Gilded kiteshield") {
            return 40
        } else if (name == "Fighter torso") {
            return 40
        } else if (name == "Granite legs") {
            return 99
        } else if (name == "Toktz-ket-xil") {
            return 60
        } else if (name == "Dharoks helm") {
            return 70
        } else if (name == "Dharoks platebody") {
            return 70
        } else if (name == "Dharoks platelegs") {
            return 70
        } else if (name == "Guthans helm") {
            return 70
        } else if (name == "Guthans platebody") {
            return 70
        } else if (name == "Guthans chainskirt") {
            return 70
        } else if (name == "Torags helm") {
            return 70
        } else if (name == "Torags platebody") {
            return 70
        } else if (name == "Torags platelegs") {
            return 70
        } else if (name == "Veracs helm") {
            return 70
        } else if (name == "Veracs brassard") {
            return 70
        } else if (name == "Veracs plateskirt") {
            return 70
        } else if (name == "Ahrims hood") {
            return 70
        } else if (name == "Ahrims robetop") {
            return 70
        } else if (name == "Ahrims robeskirt") {
            return 70
        } else if (name == "Karils coif") {
            return 70
        } else if (name == "Karils leathertop") {
            return 70
        } else if (name == "Karils leatherskirt") {
            return 70
        } else if (name == "Granite shield") {
            return 50
        } else if (name == "New crystal shield") {
            return 70
        } else if (name == "Archer helm") {
            return 45
        } else if (name == "Berserker helm") {
            return 45
        } else if (name == "Warrior helm") {
            return 45
        } else if (name == "Farseer helm") {
            return 45
        } else if (name == "Initiate helm") {
            return 20
        } else if (name == "Initiate platemail") {
            return 20
        } else if (name == "Initiate platelegs") {
            return 20
        } else if (name == "Dragonhide body") {
            return 40
        } else if (name == "Mystic hat") {
            return 20
        } else if (name == "Mystic robe top") {
            return 20
        } else if (name == "Mystic robe bottom") {
            return 20
        } else if (name == "Mystic gloves") {
            return 20
        } else if (name == "Mystic boots") {
            return 20
        } else if (name == "Enchanted hat") {
            return 20
        } else if (name == "Enchanted top") {
            return 20
        } else if (name == "Enchanted robe") {
            return 20
        } else if (name == "Splitbark helm") {
            return 40
        } else if (name == "Splitbark body") {
            return 40
        } else if (name == "Splitbark gauntlets") {
            return 40
        } else if (name == "Splitbark legs") {
            return 40
        } else if (name == "Splitbark greaves") {
            return 40
        } else if (name == "Infinity gloves") {
            return 25
        } else if (name == "Infinity hat") {
            return 25
        } else if (name == "Infinity top") {
            return 25
        } else if (name == "Infinity bottoms") {
            return 25
        } else if (name == "Infinity boots") {
            return 25
        }

        return 1
    }

    private fun getMagicRequirement(definition: ObjDefinition): Int {
        var name = definition.name
        if (name == null) {
            name = "null"
        }

        if (name == "Mystic hat") {
            return 40
        } else if (name == "Mystic robe top") {
            return 40
        } else if (name == "Mystic robe bottom") {
            return 40
        } else if (name == "Mystic gloves") {
            return 40
        } else if (name == "Mystic boots") {
            return 40
        } else if (name == "Slayer's staff") {
            return 50
        } else if (name == "Enchanted hat") {
            return 40
        } else if (name == "Enchanted top") {
            return 40
        } else if (name == "Enchanted robe") {
            return 40
        } else if (name == "Splitbark helm") {
            return 40
        } else if (name == "Splitbark body") {
            return 40
        } else if (name == "Splitbark gauntlets") {
            return 40
        } else if (name == "Splitbark legs") {
            return 40
        } else if (name == "Splitbark greaves") {
            return 40
        } else if (name == "Infinity gloves") {
            return 50
        } else if (name == "Infinity hat") {
            return 50
        } else if (name == "Infinity top") {
            return 50
        } else if (name == "Infinity bottoms") {
            return 50
        } else if (name == "Infinity boots") {
            return 50
        } else if (name == "Ahrims hood") {
            return 70
        } else if (name == "Ahrims robetop") {
            return 70
        } else if (name == "Ahrims robeskirt") {
            return 70
        } else if (name == "Ahrims staff") {
            return 70
        } else if (name == "Saradomin cape") {
            return 60
        } else if (name == "Saradomin staff") {
            return 60
        } else if (name == "Zamorak cape") {
            return 60
        } else if (name == "Zamorak staff") {
            return 60
        } else if (name == "Guthix cape") {
            return 60
        } else if (name == "Guthix staff") {
            return 60
        } else if (name == "mud staff") {
            return 30
        } else if (name == "Fire battlestaff") {
            return 30
        } else if (name == "Toktz-mej-tal") {
            return 60
        }
        return 1
    }

    private fun getPrayerRequirement(definition: ObjDefinition): Int {
        var name = definition.name
        if (name == null) {
            name = "null"
        }
        if (name.contains("Initiate")) {
            return 10
        }
        if (name.contains("Proselyte")) {
            return 20
        }
        return 1
    }

    private fun getRangedRequirement(definition: ObjDefinition): Int {
        val id = definition.id
        var name = definition.name
        if (name == null) {
            name = "null"
        }

        if (id == 2499) {
            return 50
        } else if (id == 1135) {
            return 40
        } else if (id == 1099) {
            return 40
        } else if (id == 1065) {
            return 40
        } else if (id == 2501) {
            return 60
        } else if (id == 2503) {
            return 70
        } else if (id == 2487) {
            return 50
        } else if (id == 2489) {
            return 60
        } else if (id == 2495) {
            return 60
        } else if (id == 2491) {
            return 70
        } else if (id == 2493) {
            return 50
        } else if (id == 2505) {
            return 60
        } else if (id == 2507) {
            return 70
        } else if (id == 859) {
            return 40
        } else if (id == 861) {
            return 40
        } else if (id == 7370) {
            return 40
        } else if (id == 7372) {
            return 40
        } else if (id == 7378) {
            return 40
        } else if (id == 7380) {
            return 40
        } else if (id == 7374) {
            return 50
        } else if (id == 7376) {
            return 50
        } else if (id == 7382) {
            return 50
        } else if (id == 7384) {
            return 50
        } else if (name == "Coif") {
            return 20
        } else if (name.startsWith("Studded chaps")) {
            return 20
        } else if (name.startsWith("Studded")) {
            return 20
        } else if (name == "Karils coif") {
            return 70
        } else if (name == "Karils leathertop") {
            return 70
        } else if (name == "Karils leatherskirt") {
            return 70
        } else if (name == "Robin hood hat") {
            return 40
        } else if (name == "Ranger boots") {
            return 40
        } else if (name == "Crystal bow full") {
            return 70
        } else if (name == "New crystal bow") {
            return 70
        } else if (name == "Karils crossbow") {
            return 70
        } else if (id == 2497) {
            return 70
        } else if (name == "Rune thrownaxe") {
            return 40
        } else if (name == "Rune dart") {
            return 40
        } else if (name == "Rune javelin") {
            return 40
        } else if (name == "Rune knife") {
            return 40
        } else if (name == "Adamant thrownaxe") {
            return 30
        } else if (name == "Adamant dart") {
            return 30
        } else if (name == "Adamant javelin") {
            return 30
        } else if (name == "Adamant knife") {
            return 30
        } else if (name == "Toktz-xil-ul") {
            return 60
        } else if (name == "Seercull") {
            return 50
        } else if (name == "Bolt rack") {
            return 70
        } else if (name == "Rune arrow") {
            return 40
        } else if (name == "Adamant arrow") {
            return 30
        } else if (name == "Mithril arrow") {
            return 1
        }

        return 1
    }

    private fun getStrengthRequirement(def: ObjDefinition): Int {
        var name = def.name
        if (name == null) {
            name = "null"
        }

        if (name == "Torags hammers") {
            return 70
        } else if (name == "Dharoks greataxe") {
            return 70
        } else if (name == "Granite maul") {
            return 50
        } else if (name == "Granite legs") {
            return 99
        } else if (name == "Tzhaar-ket-om") {
            return 60
        } else if (name == "Granite shield") {
            return 50
        }

        return 1
    }

    private fun getWeaponType(definition: ObjDefinition): Int {
        var name = definition.name
        if (name == null) {
            name = "null"
        }
        for (element in EquipmentConstants.CAPES) {
            if (name.contains(element)) {
                return 1
            }
        }
        for (element in EquipmentConstants.HATS) {
            if (name.contains(element)) {
                return 0
            }
        }
        for (element in EquipmentConstants.BOOTS) {
            if (name.endsWith(element) || name.startsWith(element)) {
                return 10
            }
        }
        for (element in EquipmentConstants.GLOVES) {
            if (name.endsWith(element) || name.startsWith(element)) {
                return 9
            }
        }
        for (element in EquipmentConstants.SHIELDS) {
            if (name.contains(element)) {
                return 5
            }
        }
        for (element in EquipmentConstants.AMULETS) {
            if (name.endsWith(element) || name.startsWith(element)) {
                return 2
            }
        }
        for (element in EquipmentConstants.ARROWS) {
            if (name.endsWith(element) || name.startsWith(element)) {
                return 13
            }
        }
        for (element in EquipmentConstants.RINGS) {
            if (name.endsWith(element) || name.startsWith(element)) {
                return 12
            }
        }
        for (element in EquipmentConstants.BODY) {
            if (name.contains(element)) {
                return 4
            }
        }
        for (element in EquipmentConstants.LEGS) {
            if (name.contains(element)) {
                return 7
            }
        }
        for (element in EquipmentConstants.WEAPONS) {
            if (name.endsWith(element) || name.startsWith(element)) {
                return 3
            }
        }
        return -1
    }

    private fun isFullBody(definition: ObjDefinition): Boolean {
        var name = definition.name
        if (name == null) {
            name = "null"
        }

        for (element in EquipmentConstants.FULL_BODIES) {
            if (name.contains(element)) {
                return true
            }
        }

        return false
    }

    private fun isFullHat(definition: ObjDefinition): Boolean {
        var name = definition.name
        if (name == null) {
            name = "null"
        }

        for (element in EquipmentConstants.FULL_HATS) {
            if (name.endsWith(element)) {
                return true
            }
        }

        return false
    }

    private fun isFullMask(definition: ObjDefinition): Boolean {
        var name = definition.name
        if (name == null) {
            name = "null"
        }

        for (element in EquipmentConstants.FULL_MASKS) {
            if (name.endsWith(element)) {
                return true
            }
        }

        return false
    }

    private fun isTwoHanded(definition: ObjDefinition): Boolean {
        val id = definition.id
        var name = definition.name
        if (name == null) {
            name = "null"
        }

        if (id == 4212) {
            return true
        } else if (id == 4214) {
            return true
        } else if (id == 6526) {
            return true
        } else if (name.endsWith("2h sword")) {
            return true
        } else if (name.endsWith("longbow")) {
            return true
        } else if (name == "Seercull") {
            return true
        } else if (name.endsWith("shortbow")) {
            return true
        } else if (name.endsWith("Longbow")) {
            return true
        } else if (name.endsWith("Shortbow")) {
            return true
        } else if (name.endsWith("bow full")) {
            return true
        } else if (name.endsWith("halberd")) {
            return true
        } else if (name == "Granite maul") {
            return true
        } else if (name == "Karils crossbow") {
            return true
        } else if (name == "Torags hammers") {
            return true
        } else if (name == "Veracs flail") {
            return true
        } else if (name == "Dharoks greataxe") {
            return true
        } else if (name == "Guthans warspear") {
            return true
        } else if (name == "Tzhaar-ket-om") {
            return true
        } else if (name.endsWith("godsword")) {
            return true
        } else if (name == "Saradomin sword") {
            return true
        }

        return false
    }
}