package com.example.di

import com.example.data.repository.auth.AuthRepository
import com.example.data.repository.auth.AuthRepositoryImpl
import com.example.data.repository.story.StoryRepository
import com.example.data.repository.story.StoryRepositoryImpl
import com.example.data.repository.user.UserRepository
import com.example.data.repository.user.UserRepositoryImpl
import com.example.data.service.auth.AuthServiceImpl
import com.example.data.service.story.StoryServiceImpl
import com.example.data.service.user.UserServiceImpl

object RepositoryProvider {
    fun provideAuthRepository(): AuthRepository = AuthRepositoryImpl(AuthServiceImpl())
    fun provideUserRepository(): UserRepository = UserRepositoryImpl(UserServiceImpl())
    fun provideStoryRepository(): StoryRepository = StoryRepositoryImpl(StoryServiceImpl())
}