plugins {
    kotlin("jvm")
}

group = "nulled"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    with (libs) {
        implementation(bcprov.jdk18on)
        implementation(guava)
        implementation(netty.all)
        implementation(commons.compress)
    }

    testImplementation(Kotlin.test)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}