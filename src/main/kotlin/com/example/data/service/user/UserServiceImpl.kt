package com.example.data.service.user

import com.example.data.db.DatabaseFactory.dbQuery
import com.example.data.db.Schemas.UserTable
import com.example.data.models.User
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select

class UserServiceImpl : UserService {
    override suspend fun getUser(id: Int): User {
        val userRow = dbQuery { UserTable.select { UserTable.id eq id }.first()}
        return userRow.toUser()!!
    }
}