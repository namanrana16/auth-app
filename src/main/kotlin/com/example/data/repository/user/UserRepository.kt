package com.example.data.repository.user

import com.example.base.BaseResponse

interface UserRepository {
    suspend fun getUser(id: Int): BaseResponse<Any>
}