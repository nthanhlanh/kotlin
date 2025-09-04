package com.example.kotlin.repository

import com.example.kotlin.tables.references.USERS
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class UserRepository(private val dsl: DSLContext) {

    fun findAll(): List<String> =
        dsl.select(USERS.NAME)
            .from(USERS)
            .fetchInto(String::class.java)

    fun insertUser(name: String, email: String): Int =
        dsl.insertInto(USERS, USERS.NAME, USERS.EMAIL)
            .values(name, email)
            .execute()
}
