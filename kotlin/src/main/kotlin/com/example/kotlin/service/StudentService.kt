package com.example.kotlin.service

import com.example.kotlin.dto.StudentDto
import com.example.kotlin.repository.StudentRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service

@Service
class StudentService(private val repo: StudentRepository) {

    suspend fun findById(id: Int): StudentDto? = coroutineScope {
        val dbCall = async {
            repo.findById(id) // suspend, chạy trong coroutine riêng
        }
        println("cccccccccc") // chạy ngay, không chờ DB
        dbCall.await() // đợi kết quả DB sau cùng
    }

}