package nulled.scripts.doors

import nulled.runescript.data.LocDirection.loc_east
import nulled.runescript.data.LocDirection.loc_north
import nulled.runescript.data.LocDirection.loc_south
import nulled.runescript.data.LocDirection.loc_west
import nulled.runescript.data.LocShape.wall_diagonal
import nulled.runescript.data.LocShape.wall_straight
import nulled.runescript.RuneScript
import nulled.runescript.RuneScriptException
import org.apollo.game.model.Position
import org.apollo.game.model.World
import org.apollo.game.plugin.RuneScriptContext

class door_procs(world: World, context: RuneScriptContext) : RuneScript(world, context) {
    companion object {
        fun door_open(angle: Int, shape: Int): Position {
            if (shape == wall_straight) {
                when (angle) {
                    loc_west -> return Position(-1, 0)
                    loc_north -> return Position(0, 1)
                    loc_east -> return Position(1, 0)
                    loc_south -> return Position(0, -1)
                }
            } else if (shape == wall_diagonal) {
                when (angle) {
                    loc_west -> return Position(0, 1)
                    loc_north -> return Position(1, 0)
                    loc_east -> return Position(0, -1)
                    loc_south -> return Position(-1, 0)
                }
            }
            throw RuneScriptException("invalid angle")
        }

        fun door_close(angle: Int, shape: Int): Position {
            if (shape == wall_straight) {
                when (angle) {
                    loc_west -> return Position(0, 1)
                    loc_north -> return Position(1, 0)
                    loc_east -> return Position(0, -1)
                    loc_south -> return Position(-1, 0)
                }
            } else if (shape == wall_diagonal) {
                when (angle) {
                    loc_west -> return Position(1, 0)
                    loc_north -> return Position(0, -1)
                    loc_east -> return Position(-1, 0)
                    loc_south -> return Position(0, 1)
                }
            }
            throw RuneScriptException("invalid angle")
        }
    }
}