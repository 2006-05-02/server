package nulled

import ch.qos.logback.classic.Level
import com.sun.org.slf4j.internal.LoggerFactory
import nulled.Main.version
import nulled.cache.fs.IndexedFileSystem
import org.apollo.ServerContext
import org.apollo.ServiceManager
import org.apollo.game.model.World
import org.apollo.game.plugin.PluginManager
import org.apollo.game.plugin.RuneScriptContext
import org.apollo.game.release.r377.Release377
import org.slf4j.Logger
import java.nio.file.Paths


object RuneScriptEval {
    @JvmStatic
    fun main(args: Array<String>) {
        val rootLogger: ch.qos.logback.classic.Logger = org.slf4j.LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME) as ch.qos.logback.classic.Logger
        rootLogger.level = Level.ERROR
        val world = World()
        val services = ServiceManager(world)
        val fs = IndexedFileSystem(Paths.get("data/fs", version), true)
        val context = ServerContext(Release377(), services, fs)
        world.init(fs, PluginManager(world, RuneScriptContext(context)))
    }
}