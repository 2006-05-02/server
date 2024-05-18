plugins {
    kotlin("jvm")
}

group = "nulled"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":util"))

    with (libs) {
        implementation(netty.all)
        implementation(guava)
    }

    testImplementation(Kotlin.test)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}