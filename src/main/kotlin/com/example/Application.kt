package com.example

import com.example.config.configureContentNegotiation
import com.example.config.configureDatabase
import com.example.config.configureStatusPages
import com.example.data.db.DatabaseFactory
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import com.example.security.configureSecurity

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureRouting()
    configureDatabase()
    configureContentNegotiation()
    configureStatusPages()
    configureSecurity()
    configureRouting()
}
