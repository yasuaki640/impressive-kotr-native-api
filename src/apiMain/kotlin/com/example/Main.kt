package com.example

import com.example.plugins.configureRouting
import com.example.plugins.configureSerialization
import io.ktor.server.cio.*
import io.ktor.server.engine.*


fun main() {
    embeddedServer(CIO, port = 8080) {
        configureRouting()
        configureSerialization()
    }.start(wait = true)
}
