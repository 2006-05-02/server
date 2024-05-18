package org.apollo.plugins.navigation.door

import org.apollo.game.model.Position
import org.apollo.game.model.entity.Player

open class RequirementDoor(val position: Position) {
    open fun meetsRequirement(player: Player): Boolean {
        return false
    }

    open fun failAction(player: Player) {

    }

    open fun doAction(player: Player) {}
}