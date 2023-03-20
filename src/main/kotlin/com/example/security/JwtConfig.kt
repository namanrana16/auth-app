
package com.example.security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import kotlin.reflect.jvm.isAccessible

class JwtConfig private constructor(secret: String){

    private val algorithm = Algorithm.HMAC256(secret)

    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(ISSUER)
        .withAudience(AUDIENCE)
        .build()

    fun createAccessToken(id: Int): String = JWT
        .create()
        .withIssuer(ISSUER)
        .withAudience(AUDIENCE)
        .withClaim(CLAIM, id)
        .sign(algorithm)

    companion object{
        private const val ISSUER = "auth"
        private const val AUDIENCE = "auth"
        const val CLAIM = "id"

        lateinit var instance: JwtConfig
            private set


        @OptIn(InternalCoroutinesApi::class)
        fun initialize(secret: String){
            synchronized(this){
                if(!this::instance.isAccessible){
                    instance = JwtConfig(secret)
                }
            }
        }
    }
}