package com.example

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


fun main() {
    embeddedServer(CIO, port = 8080) {
        install(ContentNegotiation) {
            json(Json)
        }

        routing() {
            get("/customers") {
                val customer = Customer(1, "tako", "uni", "kani@hotate.com")
                call.respond(customer)
            }
        }

    }.start(wait = true)

}

@Serializable
data class Customer(val id: Long, val firstName: String, val lastName: String, val email: String)