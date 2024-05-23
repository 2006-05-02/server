package nulled.cache.def

import com.google.common.base.Preconditions
import nulled.cache.DefinitionManager.npcDefinitions

class NpcDefinition(val id: Int) {
    var combatLevel: Int = -1
    var description: String? = null
    val interactions: Array<String?> = arrayOfNulls(5)
    var name: String? = null
    var size: Int = 1
    var standAnimation: Int = -1
    var walkAnimation: Int = -1
    var walkBackAnimation: Int = -1
        private set
    var walkLeftAnimation: Int = -1
        private set
    var walkRightAnimation: Int = -1
        private set

    fun getInteraction(slot: Int): String? {
        Preconditions.checkElementIndex(slot, interactions.size, "Npc interaction id is out of bounds.")
        return interactions[slot]
    }

    fun hasCombatLevel(): Boolean {
        return combatLevel != -1
    }

    fun hasInteraction(slot: Int): Boolean {
        Preconditions.checkElementIndex(slot, interactions.size, "Npc interaction id is out of bounds.")
        return interactions[slot] != null
    }

    fun hasStandAnimation(): Boolean {
        return standAnimation != -1
    }

    fun hasWalkAnimation(): Boolean {
        return walkAnimation != -1
    }

    fun hasWalkBackAnimation(): Boolean {
        return walkBackAnimation != -1
    }

    fun hasWalkLeftAnimation(): Boolean {
        return walkLeftAnimation != -1
    }

    fun hasWalkRightAnimation(): Boolean {
        return walkRightAnimation != -1
    }

    fun setInteraction(slot: Int, interaction: String?) {
        Preconditions.checkElementIndex(slot, interactions.size, "Npc interaction id is out of bounds.")
        interactions[slot] = interaction
    }

    fun setWalkAnimations(walkAnim: Int, walkBackAnim: Int, walkLeftAnim: Int, walkRightAnim: Int) {
        this.walkAnimation = walkAnim
        this.walkBackAnimation = walkBackAnim
        this.walkLeftAnimation = walkLeftAnim
        this.walkRightAnimation = walkRightAnim
    }

    companion object {
        fun count(): Int {
            return npcDefinitions!!.size
        }

        fun init(defs: Array<NpcDefinition?>) {
            npcDefinitions = defs
            for (id in defs.indices) {
                val def = defs[id]
                check(def?.id == id) { "Npc definition id mismatch." }
            }
        }

        fun lookup(id: Int): NpcDefinition? {
            Preconditions.checkElementIndex(id, npcDefinitions!!.size, "Id out of bounds.")
            return npcDefinitions!![id]
        }
    }
}