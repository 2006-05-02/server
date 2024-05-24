package nulled.cache

import nulled.cache.def.ObjDefinition
import nulled.cache.def.NpcDefinition
import nulled.cache.def.LocDefinition
import java.util.*

/**
 * This handles accessing cache data in the expected format
 * ie
 * npc("runescape_guide")
 * obj("mind_talisman")
 * loc("loc_1015")
 */
object DefinitionManager {
    var objDefinitions: Array<ObjDefinition?>? = null
    var npcDefinitions: Array<NpcDefinition?>? = null
    var locDefinitions: Array<LocDefinition?>? = null

    fun npc(name: String): NpcDefinition? {
        val results = npcDefinitions!!.filterNotNull().filter { it.name?.lowercase(Locale.getDefault())?.replace(" ", "_") == name }
        if (results.size > 1)
            throw RuntimeException("Multiple npc results found, use a finer definition")
        if (results.isEmpty())
            return null
        return results.first()
    }

    fun npc(id: Int): NpcDefinition? {
        return npcDefinitions?.filterNotNull()?.firstOrNull { it.id == id }
    }

    fun obj(name: String) : ObjDefinition? {
        val results = objDefinitions!!.filterNotNull().filter { it.name?.lowercase(Locale.getDefault())?.replace(" ", "_") == name }
        if (results.size > 1)
            throw RuntimeException("Multiple obj results found, use a finer definition")
        if (results.isEmpty())
            return null
        return results.first()
    }

    fun obj(id: Int) : ObjDefinition? {
        return objDefinitions?.filterNotNull()?.firstOrNull { it.id == id }
    }

    fun loc(name: String) : LocDefinition? {
        return locDefinitions?.filterNotNull()?.firstOrNull { "loc_${it.id}" == name }
    }

    fun loc(id: Int) : LocDefinition? {
        return locDefinitions?.filterNotNull()?.firstOrNull { it.id == id }
    }
}