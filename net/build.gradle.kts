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
    implementation(project(":util"))

    with (libs) {
        implementation(bcprov.jdk18on)
        implementation(guava)
        implementation(netty.all)
    }

    testImplementation(Kotlin.test)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}