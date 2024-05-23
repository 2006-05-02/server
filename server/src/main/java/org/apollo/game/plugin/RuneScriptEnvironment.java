package org.apollo.game.plugin;

import nulled.runescript.ScriptManager;
import org.apollo.game.model.World;
import org.apollo.game.plugin.kotlin.KotlinPluginScript;

import java.util.logging.Logger;

public class RuneScriptEnvironment implements PluginEnvironment {

	private static final Logger logger = Logger.getLogger(RuneScriptEnvironment.class.getName());
	private static final String PLUGIN_SUFFIX = "_plugin";

	private final World world;
	private RuneScriptContext context;

	public RuneScriptEnvironment(World world) {
		this.world = world;
	}

	@Override
	public void load() {
		new ScriptManager(world, context).load();
	}

	@Override
	public void setContext(RuneScriptContext context) {
		this.context = context;
	}

	private static String pluginDescriptor(Class<? extends KotlinPluginScript> clazz) {
		String className = clazz.getSimpleName();
		Package pkg = clazz.getPackage();
		return pkg == null ? className : className + " from " + pkg.getName();
	}
}
