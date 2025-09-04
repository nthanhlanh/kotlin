package com.example.kotlin.controller

import com.example.kotlin.repository.UserRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val repo: UserRepository) {

    @GetMapping
    fun getUsers(): List<String> = repo.findAll()

    @PostMapping
    fun addUser(@RequestParam name: String, @RequestParam email: String): String {
        repo.insertUser(name, email)
        return "User added"
    }
}
