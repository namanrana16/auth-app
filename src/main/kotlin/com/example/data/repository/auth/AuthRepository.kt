package com.example.data.repository.auth

import com.example.base.BaseResponse
import com.example.routes.auth.CreateUserParams
import com.example.routes.auth.UserLoginParams


interface AuthRepository {
    suspend fun registerUser(params: CreateUserParams): BaseResponse<Any>
    suspend fun loginUser(params: UserLoginParams): BaseResponse<Any>
}