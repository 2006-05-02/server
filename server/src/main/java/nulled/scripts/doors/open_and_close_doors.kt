package nulled.scripts.doors

import lostcity.engine.script.Script.Companion.FINISHED
import nulled.runescript.RuneScript
import nulled.runescript.data.Sounds
import nulled.scripts.doors.door_procs.Companion.door_open
import org.apollo.game.model.World
import org.apollo.game.model.entity.Player
import org.apollo.game.model.entity.obj.GameObject
import org.apollo.game.plugin.RuneScriptContext

class open_and_close_doors(world: World, context: RuneScriptContext) : RuneScript(world, context) {
    companion object {
        fun open_and_close_door(replacement: GameObject?, entering: Boolean, play_locked_synth: Boolean) : (Player) -> Int = { player: Player ->
            var x: Int
            var y: Int

            val coord = player.loc.position
            val angle = player.loc.angle
            val shape = player.loc.shape

            var doorDestination = door_open(angle, shape)
            if (entering) {
                //TODO
            }
            p_teleport(coord).invoke(player)

            sound_synth(Sounds.door_open, 0, 0).invoke(player)
            FINISHED
        }
    }
}