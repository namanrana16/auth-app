package com.example.data.service.story

import com.example.data.models.Story
import com.example.data.models.common.PaginatedResult
import com.example.routes.story.StoryParams
import javax.xml.stream.events.Comment

interface StoryService {
    suspend fun get(id: Int): Story?
    suspend fun getMyStories(userId: Int, page: Int, limit: Int): PaginatedResult<Story>
    suspend fun getAllStories(page: Int, limit: Int): PaginatedResult<Story>
    suspend fun getLikedStories(userId: Int): List<Story>
    suspend fun add(storyParams: StoryParams): Story?
    suspend fun update(id: Int, storyParams: StoryParams): Boolean
    suspend fun delete(storyId: Int): Boolean
    suspend fun like(userId: Int, storyId: Int): Boolean
    suspend fun comment(userId: Int, storyId: Int, comment: String): Boolean
    suspend fun getComments(storyId: Int): List<Comment>
}