plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
    id("de.fayard.refreshVersions") version "0.60.5"
}
rootProject.name = "server-377"
include("cache")
include("net")
include("server")
include("util")

