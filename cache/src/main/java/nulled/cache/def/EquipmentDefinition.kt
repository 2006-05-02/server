package nulled.cache.def

import com.google.common.base.Preconditions

class EquipmentDefinition(val id: Int) {
    private val levels = intArrayOf(1, 1, 1, 1, 1, 1, 1)
    var slot: Int = 0
    var isTwoHanded: Boolean = false
        private set
    var isFullBody: Boolean = false
        private set
    var isFullHat: Boolean = false
        private set
    var isFullMask: Boolean = false
        private set

    val attackLevel: Int
        get() = levels[ATTACK]

    val defenceLevel: Int
        get() = levels[DEFENCE]

    val hitpointsLevel: Int
        get() = levels[HITPOINTS]

    val magicLevel: Int
        get() = levels[MAGIC]

    val prayerLevel: Int
        get() = levels[PRAYER]

    val rangedLevel: Int
        get() = levels[RANGED]

    val strengthLevel: Int
        get() = levels[STRENGTH]

    fun getLevel(skill: Int): Int {
        Preconditions.checkArgument(skill >= ATTACK && skill <= MAGIC, "Skill id out of bounds.")
        return levels[skill]
    }

    fun setFlags(twoHanded: Boolean, fullBody: Boolean, fullHat: Boolean, fullMask: Boolean) {
        this.isTwoHanded = twoHanded
        this.isFullBody = fullBody
        this.isFullHat = fullHat
        this.isFullMask = fullMask
    }

    fun setLevels(attack: Int, strength: Int, defence: Int, ranged: Int, prayer: Int, magic: Int) {
        setLevels(attack, strength, defence, 1, ranged, prayer, magic)
    }

    fun setLevels(attack: Int, strength: Int, defence: Int, hitpoints: Int, ranged: Int, prayer: Int, magic: Int) {
        levels[ATTACK] = attack
        levels[STRENGTH] = strength
        levels[DEFENCE] = defence
        levels[HITPOINTS] = hitpoints
        levels[RANGED] = ranged
        levels[PRAYER] = prayer
        levels[MAGIC] = magic
    }

    companion object {
        private const val ATTACK = 0
        private const val DEFENCE = 1
        private const val STRENGTH = 2
        private const val HITPOINTS = 3
        private const val RANGED = 4
        private const val PRAYER = 5
        private const val MAGIC = 6
        private val definitions: MutableMap<Int, EquipmentDefinition> = HashMap()

        fun count(): Int {
            return definitions.size
        }

        fun init(definitions: Array<EquipmentDefinition?>) {
            for (id in definitions.indices) {
                val def = definitions[id]
                if (def != null) {
                    if (def.id != id) {
                        throw RuntimeException("Equipment definition id mismatch.")
                    }
                    Companion.definitions[def.id] = def
                }
            }
        }

        fun lookup(id: Int): EquipmentDefinition? {
            Preconditions.checkElementIndex(id, ObjDefinition.count(), "Id out of bounds.")
            return definitions[id]
        }
    }
}