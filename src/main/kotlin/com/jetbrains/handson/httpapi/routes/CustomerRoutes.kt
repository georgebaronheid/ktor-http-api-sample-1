package com.jetbrains.handson.httpapi.routes

import com.jetbrains.handson.httpapi.models.Customer
import com.jetbrains.handson.httpapi.models.customerStorage
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*


fun Route.customerRouting() {
    route("/customer") {
        get {
            if (customerStorage.isNotEmpty()) call.respond(customerStorage)
            else call.respondText("No customers found", status = HttpStatusCode.NotFound)
        }
        get("{ìd}") {
//                First check if parameter exists
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
//                if not null but doesn't exists 404
            val customer = customerStorage.find { it.id == id } ?: return@get call.respondText(
                "No customer with id $id",
                status = HttpStatusCode.NotFound
            )
            call.respond(customer)

        }
        post {
            val customer = call.receive<Customer>()
            customerStorage.add(customer)
            call.respondText("Customer stored correctly", status = HttpStatusCode.Created)

        }
        delete("{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (customerStorage.removeIf { it.id == id }) call.respondText(
                "Customer removed correctly",
                status = HttpStatusCode.Accepted
            )
            else call.respondText("Not found", status = HttpStatusCode.NotFound)
        }
    }
}

fun Application.registerCustomerRoutes() {
    routing {
        customerRouting()
    }
}
