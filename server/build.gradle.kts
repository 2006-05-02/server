plugins {
    kotlin("jvm")
}

group = "nulled"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":cache"))
    implementation(project(":net"))
    implementation(project(":util"))

    with (libs) {
        implementation(kotlin("script-runtime"))
        implementation(kotlin.reflect)
        implementation(KotlinX.coroutines.core)
        implementation(kotlin.scripting.compiler.embeddable)
        implementation(kotlin.scripting.common)
        implementation(kotlin.scripting.jvm)
        implementation(kotlin.scripting.jvm.host)
        implementation(kotlin.scripting.dependencies)
        implementation(kotlin.scripting.dependencies.maven)
        implementation(classgraph)
        implementation(scrypt)
        implementation(netty.all)
        implementation(guava)
    }

    testImplementation(Kotlin.test)
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    compilerOptions.freeCompilerArgs = listOf("-Xallow-any-scripts-in-source-roots")
    jvmToolchain(21)
}