package nulled.runescript

import io.github.classgraph.ClassGraph
import lostcity.engine.script.Script
import lostcity.engine.script.Script.Companion.ABORTED
import lostcity.engine.script.Script.Companion.RUNNING
import org.apollo.game.model.World
import org.apollo.game.model.entity.Player
import org.apollo.game.plugin.RuneScriptContext
import java.lang.reflect.Constructor


class ScriptManager(val world: World, val context: RuneScriptContext) {
    val scripts = mutableListOf<RuneScript>()

    fun load() {
        val classGraph = ClassGraph().enableAllInfo()

        try {
            classGraph.scan().use { scanResult ->
                val pluginClassList = scanResult
                    .getSubclasses(RuneScript::class.java.name)
                    .directOnly()
                for (pluginClassInfo in pluginClassList) {
                    val scriptClass = pluginClassInfo.loadClass(
                        RuneScript::class.java
                    )
                    val scriptConstructor: Constructor<RuneScript> = scriptClass.getConstructor(
                        World::class.java,
                        RuneScriptContext::class.java
                    )

                    scripts.add(scriptConstructor.newInstance(world, context))
                }
                println("loaded ${scripts.size} RuneScript objects")
            }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    companion object {
        fun Player.execute(script: Script, reset: Boolean = false, benchmark: Boolean = false) : Int {
            if (reset)
                script.reset()
            if (script.execution != RUNNING)
                script.execution = RUNNING
            return script.unit.invoke(this)
        }

        fun Player.executeScript(script: Script, protect: Boolean = false, force: Boolean = false): Int{
            return execute(script, protect, force)
        }

        fun Player.runScript(script: Script, protect: Boolean = false, force: Boolean = false): Int {
            val prevPlayer = script
            if (!force && protect && delayed)
                return ABORTED
            val result = executeScript(script, protect, force)
            return result
        }
    }
}