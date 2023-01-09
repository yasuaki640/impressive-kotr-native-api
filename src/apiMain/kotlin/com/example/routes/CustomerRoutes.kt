package com.example.routes

import com.example.models.Customer
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.customerRouting() {
    route("/customers") {
        get {
            val customer = Customer(1, "tako", "uni", "kani@hotate.com")
            call.respond(customer)
        }
        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )

            val customer = Customer(id.toLong(), "shake", "surume", "iwashi@hkatsuo.com")
            call.respond(customer)
        }
        post {

        }
        delete("{id?}") {

        }
    }
}