package org.apollo.plugins.spawn

import nulled.cache.DefinitionManager
import org.apollo.game.model.World
import org.apollo.game.model.entity.Npc
import org.apollo.game.plugin.KotlinPlugin
import org.apollo.game.plugin.RuneScriptContext
import org.apollo.plugins.spawn.npcs.RunescapeGuide
import org.apollo.plugins.spawn.npcs.SurvivalExpert

class SpawnPlugin(world: World, context: RuneScriptContext) : KotlinPlugin(
    world, context,
    name = "Spawn", author = "Apollo"
) {
    override fun start() = { world: World ->
        for ((id, name, position, facing, animation, graphic) in Spawns.list) {
            val definition = requireNotNull(id?.let(DefinitionManager::npc) ?: DefinitionManager.npc(name)) {
                "Could not find an Npc named $name to spawn."
            }

            val npc = Npc(world, definition.id, position).apply {
                turnTo(position.step(1, facing))
                animation?.let(::playAnimation)
                graphic?.let(::playGraphic)
            }

            registerSingleInstanceNpc(name, npc)

            world.register(npc)
        }
    }

    /**
     * Register any single-instance npc here for easy access later
     */
    fun registerSingleInstanceNpc(name: String, npc: Npc) {
        when (name) {
            "runescape_guide" -> RunescapeGuide.INSTANCE = npc
            "survival_expert" -> SurvivalExpert.INSTANCE = npc
        }
    }
}