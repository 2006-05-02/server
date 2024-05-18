package nulled.runescript

import kotlin.script.experimental.api.*
import kotlin.script.experimental.jvm.dependenciesFromCurrentContext
import kotlin.script.experimental.jvm.jvm

object RuneScriptDefinition : ScriptCompilationConfiguration({
    jvm {
        dependenciesFromCurrentContext(wholeClasspath = true)
        scriptFileLocation
    }
    ide {
        acceptedLocations(ScriptAcceptedLocation.Sources)
    }
    baseClass(RuneScript::class)
    fileExtension("rs2")
}) {
    private fun readResolve(): Any = RuneScriptDefinition
}