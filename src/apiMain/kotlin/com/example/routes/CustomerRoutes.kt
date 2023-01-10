package com.example.routes

import com.example.Database
import com.example.models.CustomerDTO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.customerRouting(db: Database) {
    val mapper =
        { id: Long, firstName: String, lastName: String, email: String -> CustomerDTO(id, firstName, lastName, email) }

    route("/customers") {
        get {
            val customers = db.customerQueries.selectAll(mapper).executeAsList()
            call.respond(customers)
        }
        get("{id?}") {
            val idStr = call.parameters["id"] ?: return@get call.respondText(
                "Missing id", status = HttpStatusCode.BadRequest
            )
            val id = idStr.toLong()
            val customer: CustomerDTO? = db.customerQueries.selectById(id, mapper).executeAsOneOrNull()

            if (customer == null) {
                call.respond(HttpStatusCode.NotFound, "No customer found.")
                return@get
            }
            call.respond(customer)
        }
    }
}