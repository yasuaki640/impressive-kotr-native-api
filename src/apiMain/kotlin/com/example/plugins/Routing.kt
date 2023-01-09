package com.example.plugins

import com.example.Database
import com.example.routes.customerRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(db: Database) {
    routing {
        customerRouting(db)
    }
}