package com.example.kotlin.controller

import com.example.kotlin.dto.StudentDto
import com.example.kotlin.repository.StudentRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/students")
class StudentController(private val repo: StudentRepository) {

    @GetMapping
    fun getAll() = repo.findAll()

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Int) = repo.findById(id)

    @PostMapping
    fun create(@RequestBody dto: StudentDto) = repo.create(dto)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) = repo.delete(id)

    @GetMapping("/details")
    fun getDetails() = repo.findStudentDetails()
}
