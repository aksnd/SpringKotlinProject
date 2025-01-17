package com.example.SpringKotlinWebProject.repository

import com.example.SpringKotlinWebProject.model.SiteUser
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<SiteUser, Long>{
    fun findByUsername(username: String): Optional<SiteUser>
}