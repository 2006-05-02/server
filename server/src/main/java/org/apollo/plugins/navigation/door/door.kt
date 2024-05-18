package org.apollo.plugins.navigation.door

import org.apollo.game.action.DistancedAction
import org.apollo.game.model.Direction
import org.apollo.game.model.Position
import org.apollo.game.model.World
import org.apollo.game.model.entity.Player
import org.apollo.game.model.entity.obj.DynamicGameObject
import org.apollo.game.model.entity.obj.GameObject
import org.apollo.game.model.event.PlayerEvent
import org.apollo.game.plugin.api.findObject
import org.apollo.net.message.Message
import org.apollo.plugins.navigation.door.DoorPlugin.Companion.requirementDoors

enum class DoorType { LEFT, RIGHT, NOT_SUPPORTED }

open class Door(private val gameObject: GameObject?) {

    companion object {
        val toggledDoors = hashMapOf<GameObject, GameObject>()

        private val HINGE_ORIENTATION: Map<DoorType, Map<Direction, Direction>> = mapOf(
            DoorType.LEFT to mapOf(
                Direction.NORTH to Direction.WEST,
                Direction.SOUTH to Direction.EAST,
                Direction.WEST to Direction.SOUTH,
                Direction.EAST to Direction.NORTH
            ),
            DoorType.RIGHT to mapOf(
                Direction.NORTH to Direction.EAST,
                Direction.SOUTH to Direction.WEST,
                Direction.WEST to Direction.NORTH,
                Direction.EAST to Direction.SOUTH
            )
        )

        val SUPPORTED_DOORS: Map<Int, DoorType> = mapOf(
            1516 to DoorType.LEFT, 1536 to DoorType.LEFT, 1533 to DoorType.LEFT,
            1519 to DoorType.RIGHT, 1530 to DoorType.RIGHT, 4465 to DoorType.RIGHT,
            4467 to DoorType.RIGHT, 3014 to DoorType.RIGHT, 3017 to DoorType.RIGHT,
            3018 to DoorType.RIGHT, 3019 to DoorType.RIGHT
        )

        /**
         * Find a given door in the world
         */
        fun find(world: World, position: Position, objectId: Int): Door? =
            world.findObject(position, objectId)?.let(::Door)
    }

    fun supported(): Boolean = type() !== DoorType.NOT_SUPPORTED

    fun type(): DoorType = SUPPORTED_DOORS[gameObject!!.type] ?: DoorType.NOT_SUPPORTED

    fun toggle() {
        val world = gameObject!!.world
        val region = world.regionRepository.fromPosition(gameObject.position)

        region.removeEntity(gameObject)

        val originalDoor = toggledDoors[gameObject]

        if (originalDoor == null) {
            val position = gameObject.position.step(1, Direction.WNES[gameObject.angle])
            val orientation = translateDirection()?.toOrientationInteger() ?: gameObject.angle

            val toggledDoor =
                DynamicGameObject.createPublic(world, gameObject.type, position, gameObject.shape, orientation)

            region.addEntity(toggledDoor)
            toggledDoors[toggledDoor] = gameObject
        } else {
            toggledDoors.remove(gameObject)
            region.addEntity(originalDoor)
        }
    }

    private fun translateDirection(): Direction? {
        val direction = Direction.WNES[gameObject!!.angle]
        return HINGE_ORIENTATION[type()]?.get(direction)
    }
}

class OpenDoorAction(private val player: Player, private val door: Door, position: Position) : DistancedAction<Player>(
    0, true, player, position, DISTANCE
) {
    companion object {
        const val DISTANCE = 1

        fun start(message: Message, player: Player, door: Door, position: Position) {
            player.startAction(OpenDoorAction(player, door, position))
            message.terminate()
        }
    }

    override fun executeAction() {
        if (player.world.submit(OpenDoorEvent(player))) {
            player.turnTo(position)
            val requirementDoor = requirementDoors.firstOrNull { it.position == position }
            if (requirementDoor == null)
                door.toggle()
            else
                requirementDoor.doAction(player)
        }
        stop()
    }

    override fun equals(other: Any?): Boolean {
        return other is OpenDoorAction && position == other.position && player == other.player
    }

    override fun hashCode(): Int = position.hashCode() * 31 + player.hashCode()
}

class OpenDoorEvent(player: Player) : PlayerEvent(player)
