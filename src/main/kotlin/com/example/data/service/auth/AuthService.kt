package com.example.data.service.auth

import com.example.data.models.User
import com.example.routes.auth.CreateUserParams

interface AuthService {
    suspend fun registerUser(params: CreateUserParams): User?
    suspend fun loginUser(email: String, password: String): User?
    suspend fun findUserByEmail(email: String): User?
}