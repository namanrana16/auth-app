package com.example.config

import com.example.data.db.DatabaseFactory
import com.example.di.RepositoryProvider
import com.example.routes.auth.authRoutes
import com.example.routes.story.storyRoutes
import com.example.routes.user.userRoutes
import io.ktor.server.application.*


fun configureDatabase() {
    DatabaseFactory.init()
}

fun Application.configureContentNegotiation() {
    install(ContentNegotiation) {
        jackson()
    }
}

fun Application.configureRouting(){
    authRoutes(RepositoryProvider.provideAuthRepository())
    userRoutes(RepositoryProvider.provideUserRepository())
    storyRoutes(RepositoryProvider.provideStoryRepository())
}