package com.jetbrains.handson.httpapi

import com.jetbrains.handson.httpapi.routes.registerCustomerRoutes
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    /**
     * This provides a tool for the server to inspect the header argument from a request and check if
     * it's accept argument is what we can provide.
     */
    install(ContentNegotiation) {
        json()
    }
//    Our routing that was set @{CustomerRoutes.kt}
    registerCustomerRoutes()
}
