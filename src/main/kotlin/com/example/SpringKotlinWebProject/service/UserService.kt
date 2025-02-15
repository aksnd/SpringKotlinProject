package com.example.SpringKotlinWebProject.service

import com.example.SpringKotlinWebProject.model.SiteUser
import com.example.SpringKotlinWebProject.repository.UserRepository
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun create(username: String, email: String, password: String): SiteUser {
        val encodedPassword = passwordEncoder.encode(password)
        val user = SiteUser(
            username = username,
            email = email,
            password = encodedPassword
        )
        userRepository.save(user)
        return user
    }

    fun getUser(username: String): SiteUser {
        return userRepository.findByUsername(username)
            .orElseThrow { UsernameNotFoundException("사용자를 찾을 수 없습니다.") }
    }


}

