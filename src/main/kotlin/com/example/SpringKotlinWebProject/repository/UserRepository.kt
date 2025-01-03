package com.example.SpringKotlinWebProject.repository

import com.example.SpringKotlinWebProject.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>