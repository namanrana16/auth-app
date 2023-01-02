package com.example.config

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import io.ktor.server.application.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<MismatchedInputException> { cause ->
            val error = when (cause) {
                is MissingKotlinParameterException -> BaseResponse.ErrorResponse("Missing attribute `${cause.parameter.name}`")
                else -> BaseResponse.ErrorResponse(cause.message!!)
            }
            call.respond(error.statusCode, error)
        }
        exception<JsonParseException> { cause ->
            call.respond(BaseResponse.ErrorResponse(cause.message!!))
        }
    }
}