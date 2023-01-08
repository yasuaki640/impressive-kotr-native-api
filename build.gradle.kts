val ktor_version: String = "2.2.2"

plugins {
    application
    kotlin("multiplatform") version "1.7.21"
}

group = "me.yasuaki640"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

kotlin {
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val apiTarget = when {
        hostOs == "Mac OS X" -> macosX64("api")
        hostOs == "Linux" -> linuxX64("api")
        isMingwX64 -> mingwX64("api")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    apiTarget.apply {
        binaries {
            executable {
                entryPoint = "main"
            }
        }
    }
    sourceSets {
        val apiMain by getting {
            dependencies {
                implementation("io.ktor:ktor-server-core:$ktor_version")
                implementation("io.ktor:ktor-server-cio:$ktor_version")
            }
        }
        val apiTest by getting
    }
}
