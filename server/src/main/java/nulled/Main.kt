package nulled

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import nulled.cache.DefinitionManager
import nulled.cache.fs.IndexedFileSystem
import nulled.scripts.tutorial.data.TutorialConstants
import org.apollo.ServerContext
import org.apollo.ServiceManager
import org.apollo.game.model.World
import org.apollo.game.model.entity.Npc
import org.apollo.game.plugin.RuneScriptContext
import org.apollo.game.plugin.PluginManager
import org.apollo.game.release.r377.Release377
import org.apollo.game.session.ApolloHandler
import org.apollo.net.HttpChannelInitializer
import org.apollo.net.JagGrabChannelInitializer
import org.apollo.net.NetworkConstants
import org.apollo.net.ServiceChannelInitializer
import org.apollo.plugins.spawn.Spawns
import org.apollo.plugins.spawn.npcs.RunescapeGuide
import org.apollo.plugins.spawn.npcs.SurvivalExpert
import java.io.IOException
import java.net.InetSocketAddress
import java.net.SocketAddress
import java.nio.file.Paths
import java.util.logging.Level
import java.util.logging.Logger

object Main {
    val version = "377"
    private val httpBootstrap: ServerBootstrap = ServerBootstrap()
    private val jaggrabBootstrap: ServerBootstrap = ServerBootstrap()
    private val loopGroup: EventLoopGroup = NioEventLoopGroup()
    private val logger: Logger = Logger.getLogger(Main::class.java.getName())

    /**
     * The [ServerBootstrap] for the service listener.
     */
    private val serviceBootstrap: ServerBootstrap = ServerBootstrap()
    @JvmStatic
    fun main(args: Array<String>) {
        init()
        val service: SocketAddress = InetSocketAddress(NetworkConstants.SERVICE_PORT)
        val http: SocketAddress = InetSocketAddress(NetworkConstants.HTTP_PORT)
        val jaggrab: SocketAddress = InetSocketAddress(NetworkConstants.JAGGRAB_PORT)

        bind(service, http, jaggrab)
    }

    fun init() {
        serviceBootstrap.group(loopGroup)
        httpBootstrap.group(loopGroup)
        jaggrabBootstrap.group(loopGroup)

        val world = World()
        val services = ServiceManager(world)
        val fs = IndexedFileSystem(Paths.get("data/fs", version), true)
        val context = ServerContext(Release377(), services, fs)
        val handler = ApolloHandler(context)

        val service: ChannelInitializer<io.netty.channel.socket.SocketChannel> = ServiceChannelInitializer(handler)
        serviceBootstrap.channel(NioServerSocketChannel::class.java)
        serviceBootstrap.childHandler(service)

        val http: ChannelInitializer<io.netty.channel.socket.SocketChannel> = HttpChannelInitializer(handler)
        httpBootstrap.channel(NioServerSocketChannel::class.java)
        httpBootstrap.childHandler(http)

        val jaggrab: ChannelInitializer<io.netty.channel.socket.SocketChannel> = JagGrabChannelInitializer(handler)
        jaggrabBootstrap.channel(NioServerSocketChannel::class.java)
        jaggrabBootstrap.childHandler(jaggrab)

        val manager = PluginManager(world, RuneScriptContext(context))
        services.startAll()

        world.init(fs, manager)
        //TODO put this somewhere else
        spawnNPCs(world)
    }

    fun spawnNPCs(world: World) {
        for ((id, name, position, facing, animation, graphic) in Spawns.list) {
            val definition = requireNotNull(id?.let(DefinitionManager::npc) ?: DefinitionManager.npc(name)) {
                "Could not find an Npc named $name to spawn."
            }

            val npc = Npc(world, definition.id, position).apply {
                turnTo(position.step(1, facing))
                animation?.let(::playAnimation)
                graphic?.let(::playGraphic)
            }

            registerSingleInstanceNpc(name, npc)

            world.register(npc)
        }
    }

    /**
     * Register any single-instance npc here for easy access later
     */
    fun registerSingleInstanceNpc(name: String, npc: Npc) {
        when (name.toLowerCase().replace(" ", "_")) {
            TutorialConstants.runescape_guide -> RunescapeGuide.INSTANCE = npc
            TutorialConstants.survival_expert -> SurvivalExpert.INSTANCE = npc
        }
    }

    @Throws(IOException::class)
    fun bind(service: SocketAddress, http: SocketAddress, jaggrab: SocketAddress) {
        logger.fine("Binding service listener to address: $service...")
        bind(serviceBootstrap, service)

        try {
            logger.fine("Binding HTTP listener to address: $http...")
            bind(httpBootstrap, http)
        } catch (cause: IOException) {
            logger.log(
                Level.WARNING,
                "Unable to bind to HTTP - JAGGRAB will be used as a fallback.",
                cause
            )
        }

        logger.fine("Binding JAGGRAB listener to address: $jaggrab...")
        bind(jaggrabBootstrap, jaggrab)

        logger.info("Ready for connections.")
    }

    @Throws(IOException::class)
    private fun bind(bootstrap: ServerBootstrap, address: SocketAddress) {
        try {
            bootstrap.bind(address).sync()
        } catch (cause: Exception) {
            throw IOException("Failed to bind to $address", cause)
        }
    }
}