package com.example.data.service.user

import com.example.data.models.User

interface UserService {
    suspend fun getUser(id: Int): User
}