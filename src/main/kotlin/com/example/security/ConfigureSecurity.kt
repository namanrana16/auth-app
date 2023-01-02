package com.example.security

import io.ktor.auth.*
import io.ktor.server.application.*

fun Application.configureSecurity(){
    JwtConfig.initialize("my-story-app")
    install(Authentication){
        jwt {
            verifier(JwtConfig.instance.verifier)
            validate {
                val claim = it.payload.getClaim(JwtConfig.CLAIM).asInt()
                if(claim != null){
                    UserIdPrincipalForUser(claim)
                }else {
                    null
                }
            }
            challenge { defaultScheme, realm ->
                call.respond(BaseResponse.ErrorResponse(INVALID_AUTHENTICATION_TOKEN))
            }
        }
    }
}