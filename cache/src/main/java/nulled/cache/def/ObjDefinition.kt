package nulled.cache.def

import com.google.common.base.Preconditions
import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap
import nulled.cache.DefinitionManager.objDefinitions

class ObjDefinition(val id: Int) {
    var countobj: IntArray? = null
    var countco: IntArray? = null
    var zan2d: Int = -1
    var womanhead2: Int = -1
    var manhead2: Int = -1
    var womanhead: Int = -1
    var manhead: Int = -1
    var womanwear3: Int = -1
    var manwear3: Int = -1
    var recol_s: IntArray? = null
    var recol_d: IntArray? = null
    var womanwear2: Int = -1
    var womanwearOffsetY: Int = -1
    var womanwear: Int = -1
    var manwear2: Int = -1
    var manwearOffsetY: Int = -1
    var manwear: Int = -1
    var yof2d: Int = -1
    var xof2d: Int = -1
    var yan2d: Int = -1
    var xan2d: Int = -1
    var zoom2d: Int = -1
    var model: Int = -1
    var desc: String? = null
    private val groundActions = arrayOfNulls<String>(5)
    private val inventoryActions = arrayOfNulls<String>(5)
    var members: Boolean = false
    var name: String? = null
    var certtemplate: Int = -1
    var certlink: Int = -1
    var stackable: Boolean = false
    var team: Int = 0
    var cost: Int = 1

    fun getGroundAction(id: Int): String? {
        Preconditions.checkElementIndex(id, groundActions.size, "Ground action id is out of bounds.")
        return groundActions[id]
    }

    fun getInventoryAction(id: Int): String? {
        Preconditions.checkElementIndex(id, inventoryActions.size, "Inventory action id is out of bounds.")
        return inventoryActions[id]
    }

    val isNote: Boolean
        get() = certtemplate != -1 && certlink != -1

    fun setGroundAction(id: Int, action: String?) {
        Preconditions.checkElementIndex(id, groundActions.size, "Ground action id is out of bounds.")
        groundActions[id] = action
    }

    fun setInventoryAction(id: Int, action: String?) {
        Preconditions.checkElementIndex(id, inventoryActions.size, "Inventory action id is out of bounds.")
        inventoryActions[id] = action
    }

    fun toNote() {
        if (isNote) {
            if (desc != null && desc!!.startsWith("Swap this note at any bank for ")) {
                return  // already converted.
            }

            val infoDef = lookup(certlink)!!
            name = infoDef.name
            members = infoDef.members

            var prefix = "a"
            val firstChar = if (name == null) 'n' else name!![0]

            if (firstChar == 'A' || firstChar == 'E' || firstChar == 'I' || firstChar == 'O' || firstChar == 'U') {
                prefix = "an"
            }

            desc = "Swap this note at any bank for $prefix $name."
            cost = infoDef.cost
            stackable = true
        } else {
            throw IllegalStateException("Item cannot be noted.")
        }
    }

    companion object {
        private val notes: BiMap<Int, Int> = HashBiMap.create()
        private val notesInverse: BiMap<Int, Int> = notes.inverse()

        fun count(): Int {
            return objDefinitions!!.size
        }

        fun init(defs: Array<ObjDefinition?>) {
            objDefinitions = defs
            for (id in defs.indices) {
                val def = defs[id]
                if (def?.id != id) {
                    throw RuntimeException("Item definition id mismatch.")
                }
                if (def.isNote) {
                    def.toNote()
                    notes[def.certlink] = def.id
                }
            }
        }

        fun itemToNote(id: Int): Int {
            val entry = notes[id] ?: return id
            return entry
        }

        fun lookup(id: Int): ObjDefinition? {
            Preconditions.checkElementIndex(id, objDefinitions!!.size, "Id out of bounds.")
            return objDefinitions!![id]
        }

        fun noteToItem(id: Int): Int {
            val entry = notesInverse[id] ?: return id
            return entry
        }
    }
}
