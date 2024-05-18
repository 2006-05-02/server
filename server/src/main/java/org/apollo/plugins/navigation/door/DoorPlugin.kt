package org.apollo.plugins.navigation.door

import org.apollo.game.message.impl.ObjectActionMessage
import org.apollo.game.model.Position
import org.apollo.game.model.World
import org.apollo.game.model.entity.Player
import org.apollo.game.plugin.KotlinPlugin
import org.apollo.game.plugin.RuneScriptContext
import org.apollo.plugins.navigation.door.doors.TutorialDoor1

class DoorPlugin(world: World, context: RuneScriptContext) : KotlinPlugin(
    world, context,
    name = "Doors", author = "Apollo"
) {
    companion object {
        val requirementDoors = arrayOf<RequirementDoor>(TutorialDoor1)
    }

    override fun onObjectFirstAction() = unit@{ player: Player, message: ObjectActionMessage ->
        val door = Door.find(player.world, message.position, message.id) ?: return@unit
        if (door.supported() && playerMeetsRequirement(player, message.position)) {
            player.sendMessage("door: x:${message.position.x} y:${message.position.y}")
            OpenDoorAction.start(message, player, door, message.position)
        }
    }

    fun playerMeetsRequirement(player: Player, position: Position): Boolean {
        for (door in requirementDoors) {
            if (door.position == position) {
                return door.meetsRequirement(player)
            }
        }
        return true
    }
}