package com.example.SpringKotlinWebProject.service

import com.example.SpringKotlinWebProject.model.User
import com.example.SpringKotlinWebProject.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {
    fun getAllUsers(): List<User> = userRepository.findAll()
    fun getUserById(id: Long): User? = userRepository.findById(id).orElse(null)
    fun createUser(user: User): User = userRepository.save(user)

}