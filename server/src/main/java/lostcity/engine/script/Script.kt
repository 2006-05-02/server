package lostcity.engine.script

import org.apollo.game.model.entity.Player

class Script(var unit: (Player) -> Int) {
    var execution: Int = RUNNING
    fun reset() {

    }

    companion object {
        const val ABORTED = -1;
        const val RUNNING = 0;
        const val FINISHED = 1;
        const val SUSPENDED = 2; // suspended to move to player
        const val PAUSEBUTTON = 3;
        const val COUNTDIALOG = 4;
        const val NPC_SUSPENDED = 5; // suspended to move to npc
        const val WORLD_SUSPENDED = 6; // suspended to move to world
    }
}