package org.apollo.game.plugin

import org.apollo.game.message.impl.*
import org.apollo.game.model.World
import org.apollo.game.model.entity.Player
import org.apollo.game.model.event.impl.LoginEvent
import org.apollo.game.model.event.impl.LogoutEvent
import org.apollo.game.plugin.kotlin.KotlinPluginScript

open class KotlinPlugin(world: World, context: RuneScriptContext,
                        val name: String = "",
                        val author: String = "") : KotlinPluginScript(world, context) {

    init {
        subscribe()
    }

    private fun subscribe() {
        start()?.let { action ->
            start { action.invoke(it) }
        }

        spawnNpcs()?.invoke()

        subscribeObjectAction(1, onObjectFirstAction())
        subscribeObjectAction(2, onObjectSecondAction())
        subscribeObjectAction(3, onObjectThirdAction())

        onNpcAction()?.let { action ->
            on { NpcActionMessage::class }
                .then { action.invoke(it, this) }
        }
        subscribeNpcAction(1, onNpcFirstAction())
        subscribeNpcAction(2, onNpcSecondAction())
        subscribeNpcAction(3, onNpcThirdAction())

        onFlashingTabClicked()?.let { action ->
            on { FlashingTabClickedMessage::class }
                .then { action.invoke(it, this) }
        }

        onWalkAction()?.let { action ->
            on { WalkMessage::class }.then { action.invoke(it, this) }
        }

        onLogin()?.let { action ->
            world.listenFor(LoginEvent::class.java) {
                action.invoke(it)
            }
        }

        onButton()?.let { action ->
            on { ButtonMessage::class }
                .then { action.invoke(it, this) }
        }

        onRegionChanged()?.let { action ->
            on { RegionChangeMessage::class }
                .then { action.invoke(it, this) }
        }

        onLogout()?.let { action ->
            world.listenFor(LogoutEvent::class.java) {
                action.invoke(it)
            }
        }
    }

    fun subscribeObjectAction(option: Int, action: ((Player, ObjectActionMessage) -> Unit)?) {
        action?.let {
            on { ObjectActionMessage::class }
                .where { this.option == option }
                .then { action.invoke(it, this) }
        }
    }

    fun subscribeNpcAction(option: Int, action: ((Player, NpcActionMessage) -> Unit)?) {
        action?.let {
            on { NpcActionMessage::class }
                .where { this.option == option }
                .then { action.invoke(it, this) }
        }
    }

    open fun onWalkAction(): ((Player, WalkMessage) -> Unit)? = null

    open fun onObjectFirstAction(): ((Player, ObjectActionMessage) -> Unit)? = null
    open fun onObjectSecondAction(): ((Player, ObjectActionMessage) -> Unit)? = null
    open fun onObjectThirdAction(): ((Player, ObjectActionMessage) -> Unit)? = null
    open fun onNpcAction(): ((Player, NpcActionMessage) -> Unit)? = null
    open fun onNpcFirstAction(): ((Player, NpcActionMessage) -> Unit)? = null
    open fun onNpcSecondAction(): ((Player, NpcActionMessage) -> Unit)? = null
    open fun onNpcThirdAction(): ((Player, NpcActionMessage) -> Unit)? = null
    open fun onFlashingTabClicked(): ((Player, FlashingTabClickedMessage) -> Unit)? = null
    open fun onRegionChanged(): ((Player, RegionChangeMessage) -> Unit)? = null
    open fun onButton(): ((Player, ButtonMessage) -> Unit)? = null
    open fun spawnNpcs():(() -> Unit)? = null
    open fun start(): ((World) -> Unit)? = null
    open fun onLogin(): ((LoginEvent) -> Unit)? = null
    open fun onLogout(): ((LogoutEvent) -> Unit)? = null
}