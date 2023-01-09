val ktor_version: String = "2.2.2"
val sqldelight_version: String = "1.5.4"

plugins {
    application
    id("com.squareup.sqldelight") version "1.5.4"
    kotlin("multiplatform") version "1.8.0"
    kotlin("plugin.serialization") version "1.8.0"
}

group = "me.yasuaki640"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

sqldelight {
    database("Database") {
        packageName = "com.example"
        verifyMigrations = true
    }
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
                entryPoint = "com.example.main"
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            // SQLDelight ORM will be generated here
        }
        val apiMain by getting {
            dependencies {
                implementation("com.squareup.sqldelight:native-driver:$sqldelight_version")
                implementation("io.ktor:ktor-server-core:$ktor_version")
                implementation("io.ktor:ktor-server-cio:$ktor_version")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
                implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
            }
        }
        val apiTest by getting
    }
}
