package com.example.data.service.story

import com.example.data.db.DatabaseFactory
import com.example.data.db.Schemas.CommentTable
import com.example.data.db.Schemas.LikeTable
import com.example.data.db.Schemas.StoryTable
import com.example.data.db.Schemas.StoryTable.content
import com.example.data.db.Schemas.StoryTable.isDraft
import com.example.data.db.Schemas.StoryTable.title
import com.example.data.db.Schemas.StoryTable.userId
import com.example.data.db.Schemas.UserTable
import com.example.data.models.Comment
import com.example.data.models.Story
import com.example.data.models.common.PaginatedResult
import com.example.routes.story.StoryParams
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.alias
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.InsertStatement

class StoryServiceImpl : StoryService {

    override suspend fun get(id: Int): Story? {
        val storyRow = DatabaseFactory.dbQuery { StoryTable.select { StoryTable.id eq id }.first() }
        return storyRow.toStory()
    }

    override suspend fun getMyStories(userId: Int, page: Int, limit: Int): PaginatedResult<Story> {
        var pageCount: Long = 0
        var nextPage: Long? = null

        val stories = DatabaseFactory.dbQuery {
            StoryTable
                .innerJoin(UserTable, { UserTable.id }, { StoryTable.userId })
                .select { StoryTable.userId eq userId }.orderBy(StoryTable.createdAt, SortOrder.DESC).also {
                    pageCount = it.count() / limit
                    if (page < pageCount)
                        nextPage = page + 1L
                }.limit(limit, (limit * page).toLong())
                .mapNotNull { it.toStoryJoinedWithUser() }
        }
        return PaginatedResult(pageCount, nextPage, stories)
    }

    override suspend fun getAllStories(page: Int, limit: Int): PaginatedResult<Story> {
        var pageCount: Long = 0
        var nextPage: Long? = null

        val stories = DatabaseFactory.dbQuery {
            StoryTable
                .innerJoin(UserTable, { UserTable.id }, { StoryTable.userId })
                .selectAll().orderBy(StoryTable.createdAt, SortOrder.DESC).also {
                    pageCount = it.count() / limit
                    if (page < pageCount)
                        nextPage = page + 1L
                }.limit(limit, (limit * page).toLong())
                .mapNotNull { it.toStoryJoinedWithUser() }
        }
        return PaginatedResult(pageCount, nextPage, stories)
    }

    override suspend fun getLikedStories(userId: Int): List<Story> {
        return DatabaseFactory.dbQuery {
            val storyTable = StoryTable.alias("s")
            LikeTable.innerJoin(storyTable, { LikeTable.storyId }, { storyTable[StoryTable.id] })
                .select { LikeTable.userId eq userId }
                .mapNotNull { it.toStory() }
        }
    }

    override suspend fun add(storyParams: StoryParams): Story? {
        var statement: InsertStatement<Number>? = null
        DatabaseFactory.dbQuery {
            statement = StoryTable.insert {
                it[userId] = storyParams.userId
                it[title] = storyParams.title
                it[content] = storyParams.content
                it[isDraft] = storyParams.isDraft
            }
        }
        return statement?.resultedValues?.get(0).toStory()
    }

    override suspend fun update(id: Int, storyParams: StoryParams): Boolean {
        var result = -1
        DatabaseFactory.dbQuery {
            result = StoryTable.update({ StoryTable.id eq id }) {
                it[userId] = storyParams.userId
                it[title] = storyParams.title
                it[content] = storyParams.content
                it[isDraft] = storyParams.isDraft
            }
        }
        return result == 1
    }

    override suspend fun delete(storyId: Int): Boolean {
        var result = -1
        DatabaseFactory.dbQuery {
            result = StoryTable.deleteWhere { StoryTable.id eq storyId }
        }
        return result == 1
    }

    override suspend fun like(userId: Int, storyId: Int): Boolean {
        var statement: InsertStatement<Number>? = null
        DatabaseFactory.dbQuery {
            statement = LikeTable.insert {
                it[this.userId] = userId
                it[this.storyId] = storyId
            }
        }
        return statement?.resultedValues?.isNotEmpty() ?: false
    }

    override suspend fun comment(userId: Int, storyId: Int, comment: String): Boolean {
        var statement: InsertStatement<Number>? = null
        DatabaseFactory.dbQuery {
            statement = CommentTable.insert {
                it[this.userId] = userId
                it[this.storyId] = storyId
                it[this.comment] = comment
            }
        }
        return statement?.resultedValues?.isNotEmpty() ?: false
    }

    override suspend fun getComments(storyId: Int): List<Comment> {
        return DatabaseFactory.dbQuery {
            CommentTable.select { CommentTable.storyId eq storyId }.mapNotNull {
                it.toComment()
            }
        }
    }
}