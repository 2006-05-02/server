package nulled.cache.def

import com.google.common.base.Preconditions
import nulled.cache.DefinitionManager.locDefinitions

class LocDefinition(val id: Int) {
    var code75: Int = -1
    var code74: Boolean = false
    var forcedecor: Boolean = false
    var offsetz: Int = -1
    var offsety: Int = -1
    var offsetx: Int = -1
    var forceapproach: Int = -1
    var mapscene: Int = -1
    var resizez: Int = -1
    var resizey: Int = -1
    var resizex: Int = -1
    var shadow: Boolean = true
    var mirror: Boolean = false
    var mapfunction: Int = -1
    var recol_s: IntArray? = null
    var recol_d: IntArray? = null
    var contrast: Int = -1
    var ambient: Int = -1
    var wallwidth: Int = -1
    var anim: Int = -1
    var occlude: Boolean = false
    var sharelight: Boolean = false
    var hillskew: Boolean = false
    var code5: IntArray? = null
    var code1: Int = -1
    var desc: String? = null
    var blockrange: Boolean = true
    var active: Boolean = false
    var isObstructive: Boolean = false
    var length: Int = 1
    var menuActions: Array<String?>? = null
    var name: String? = null
    var blockwalk: Boolean = true
    var width: Int = 1

    companion object {
        fun count(): Int {
            return locDefinitions!!.size
        }
        fun init(defs: Array<LocDefinition?>) {
            locDefinitions = defs
            for (id in defs.indices) {
                val def = defs[id]
                if (def?.id != id) {
                    throw RuntimeException("Item definition id mismatch.")
                }
            }
        }
        fun lookup(id: Int): LocDefinition? {
            Preconditions.checkElementIndex(id, locDefinitions!!.size, "Id out of bounds.")
            return locDefinitions!![id]
        }
    }
}