package com.example.data.db.extension

import com.example.data.db.Schemas.CommentTable
import com.example.data.db.Schemas.StoryTable
import com.example.data.db.Schemas.UserTable
import com.example.data.models.Comment
import com.example.data.models.Story
import com.example.data.models.User
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow?.toUser(): User? {
    return if (this == null) null
    else User(
        id = this[UserTable.id],
        fullName = this[UserTable.fullName],
        avatar = this[UserTable.avatar],
        email = this[UserTable.email],
        createdAt = this[UserTable.createdAt].toString(),
    )
}

fun ResultRow?.toStory(): Story? {
    return if (this == null) null
    else Story(
        id = this[StoryTable.id],
        title = this[StoryTable.title],
        content = this[StoryTable.content],
        isDraft = this[StoryTable.isDraft],
        createdAt = this[StoryTable.createdAt].toString()
    )
}

fun ResultRow?.toStoryJoinedWithUser(): Story? {
    return if (this == null) null
    else Story(
        id = this[StoryTable.id],
        user = User(
            id = this[UserTable.id],
            fullName = this[UserTable.fullName],
            avatar = this[UserTable.avatar],
            email = this[UserTable.email],
            createdAt = this[UserTable.createdAt].toString()
        ),
        title = this[StoryTable.title],
        content = this[StoryTable.content],
        isDraft = this[StoryTable.isDraft],
        createdAt = this[StoryTable.createdAt].toString()
    )
}

fun ResultRow?.toComment(): Comment? {
    return if (this == null) null
    else Comment(
        id = this[CommentTable.id],
        userId = this[CommentTable.userId],
        storyId = this[CommentTable.storyId],
        comment = this[CommentTable.comment],
        createdAt = this[CommentTable.createdAt].toString()
    )
}