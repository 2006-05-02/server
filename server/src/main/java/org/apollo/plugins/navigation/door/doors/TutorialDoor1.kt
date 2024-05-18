package org.apollo.plugins.navigation.door.doors

import org.apollo.game.model.Position
import org.apollo.game.model.entity.Player
import org.apollo.plugins.navigation.door.RequirementDoor

object TutorialDoor1 : RequirementDoor(Position(3098, 3107)) {
    override fun meetsRequirement(player: Player): Boolean {
        return false
    }

    override fun doAction(player: Player) {

    }
}