package com.example.routes

import com.example.Database
import com.example.models.CustomerDTO
import com.example.models.toEntity
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.customerRouting(db: Database) {
    val toDto =
        { id: Long, firstName: String, lastName: String, email: String -> CustomerDTO(id, firstName, lastName, email) }

    route("/customers") {
        post {
            val input = call.receiveNullable<CustomerDTO>()
            if (input == null) {
                call.respond(HttpStatusCode.UnprocessableEntity, "No parameter found.")
                return@post
            }

            val customer = input.toEntity()
            db.customerQueries.insert(customer)
            call.respond(HttpStatusCode.Created, "A customer created successfully")
        }
        get {
            val customers = db.customerQueries.selectAll(toDto).executeAsList()
            call.respond(customers)
        }
        get("{id?}") {
            val idStr = call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest,
            )
            val id = idStr.toLong()
            val customer: CustomerDTO? = db.customerQueries.selectById(id, toDto).executeAsOneOrNull()

            if (customer == null) {
                call.respond(HttpStatusCode.NotFound, "No customer found.")
                return@get
            }
            call.respond(customer)
        }
        delete("{id?}") {
            val idStr = call.parameters["id"] ?: return@delete call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest,
            )
            val id = idStr.toLong()

            db.customerQueries.deleteById(id)
            call.respond("Successfully deleted.")
        }
    }
}
