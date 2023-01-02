package com.example.data.repository.story

import com.example.base.BaseResponse
import com.example.routes.story.StoryParams

interface StoryRepository {

    suspend fun getMyStories(userId: Int, page: Int, limit: Int): BaseResponse<Any>
    suspend fun getAllStories(page: Int, limit: Int): BaseResponse<Any>
    suspend fun add(storyParams: StoryParams): BaseResponse<Any>
    suspend fun update(id: Int, storyParams: StoryParams): BaseResponse<Any>
    suspend fun delete(storyId: Int): BaseResponse<Any>
    suspend fun like(userId: Int, storyId: Int): BaseResponse<Any>
    suspend fun comment(userId: Int, storyId: Int, comment: String): BaseResponse<Any>
    suspend fun getComments(storyId: Int): BaseResponse<Any>
}