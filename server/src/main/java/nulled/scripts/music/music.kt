package nulled.scripts.music

import lostcity.engine.script.Script.Companion.FINISHED
import lostcity.engine.script.ServerTriggerType
import nulled.runescript.RuneScript
import org.apollo.game.message.impl.PlaySongMessage
import org.apollo.game.model.Position
import org.apollo.game.model.World
import org.apollo.game.model.entity.Player
import org.apollo.game.plugin.RuneScriptContext
import org.apollo.plugins.music.songs.NewbieMelody

class music(world: World, context: RuneScriptContext) : RuneScript(world, context) {
    val songs = arrayListOf(
        NewbieMelody
    )
    init {
        on(ServerTriggerType.LOGIN, "_") { player: Player ->
            val playerPosition = player.position
            val regionPosition = Position(playerPosition.regionCoordinates.x, playerPosition.regionCoordinates.y)
            findAndPlaySong(player, regionPosition)
            return@on FINISHED
        }
    }

    fun findAndPlaySong(player: Player, regionPosition: Position) {
        var found = false
        outer@for (song in songs) {
            for (region in song.regions) {
                if (region == regionPosition) {
                    player.send(PlaySongMessage(song.id, 0))
                    found = true
                    break@outer
                }
            }
        }
        if (!found)
            println("No song found for regionPosition: x:${regionPosition.x} y:${regionPosition.y}")
    }
}