package org.apollo.game.plugin;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost;
import nulled.runescript.RuneScript;
import nulled.runescript.ScriptManager;
import org.apollo.game.model.World;
import org.apollo.game.plugin.kotlin.KotlinPluginScript;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
