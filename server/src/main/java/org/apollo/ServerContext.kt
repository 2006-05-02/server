package org.apollo

import org.apollo.cache.IndexedFileSystem
import org.apollo.game.service.GameService
import org.apollo.game.service.LoginService
import org.apollo.game.service.UpdateService
import org.apollo.net.release.Release
import java.util.*

/**
 * A [ServerContext] is created along with the [Server] object. The primary difference is that a reference
 * to the current context should be passed around within the server. The [Server] should not be as it allows
 * access to some methods such as [Server.bind] which user scripts/code should not be able to access.
 *
 * @author Graham
 * @author Major
 */
class ServerContext(release: Release, services: ServiceManager, fileSystem: IndexedFileSystem) {
    /**
     * Gets the IndexeFileSystem
     *
     * @return The IndexedFileSystem.
     */
    /**
     * The IndexedFileSystem.
     */
    val fileSystem: IndexedFileSystem

    /**
     * Gets the current release.
     *
     * @return The current release.
     */
    /**
     * The current release.
     */
    val release: Release = Objects.requireNonNull(release)

    /**
     * The service manager.
     */
    private val services: ServiceManager = Objects.requireNonNull(services)

    /**
     * Creates a new server context.
     *
     * @param release The current release.
     * @param services The service manager.
     * @param fileSystem The indexed file system.
     */
    init {
        this.services.setContext(this)
        this.fileSystem = Objects.requireNonNull(fileSystem)
    }

    val gameService: GameService
        /**
         * Gets the [GameService].
         *
         * @return The GameService.
         */
        get() = services.game

    val loginService: LoginService
        /**
         * Gets the [LoginService].
         *
         * @return The LoginService.
         */
        get() = services.login

    val updateService: UpdateService
        /**
         * Gets the [UpdateService].
         *
         * @return The UpdateService.
         */
        get() = services.update
}