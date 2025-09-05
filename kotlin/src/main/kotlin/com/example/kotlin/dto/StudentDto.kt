package com.example.kotlin.dto

data class StudentDto(
    val id: Int? = null,
    val name: String,
    val email: String,
    val dateOfBirth: java.time.LocalDate,
    val classId: Int
)
