package org.apollo.plugins.music

import org.apollo.game.message.impl.PlaySongMessage
import org.apollo.game.message.impl.RegionChangeMessage
import org.apollo.game.model.Position
import org.apollo.game.model.World
import org.apollo.game.model.entity.Player
import org.apollo.game.model.event.impl.LoginEvent
import org.apollo.game.plugin.KotlinPlugin
import org.apollo.game.plugin.RuneScriptContext
import org.apollo.plugins.music.songs.NewbieMelody

class MusicPlugin(world: World, context: RuneScriptContext) : KotlinPlugin(
    world, context,
    name = "Music", author = "Null"
) {
    val songs = arrayListOf(
        NewbieMelody
    )

    override fun onLogin() = { event: LoginEvent ->
        val playerPosition = event.player.position
        val regionPosition = Position(playerPosition.regionCoordinates.x, playerPosition.regionCoordinates.y)
        findAndPlaySong(event.player, regionPosition)
    }

    override fun onRegionChanged() = { player: Player, event: RegionChangeMessage ->
        val regionPosition = Position(event.position.regionCoordinates.x, event.position.regionCoordinates.y)
        findAndPlaySong(player, regionPosition)
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