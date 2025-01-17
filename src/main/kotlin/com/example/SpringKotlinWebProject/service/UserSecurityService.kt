package com.example.SpringKotlinWebProject.service

import com.example.SpringKotlinWebProject.others.UserRole
import com.example.SpringKotlinWebProject.repository.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserSecurityService(private val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val siteUser = userRepository.findByUsername(username)
            .orElseThrow {UsernameNotFoundException("사용자를 찾을 수 없습니다.") }

        val authorities = mutableListOf<GrantedAuthority>().apply {
            if (username == "admin") {
                add(SimpleGrantedAuthority(UserRole.ADMIN.value))
            } else {
                add(SimpleGrantedAuthority(UserRole.USER.value))
            }
        }

        return User(siteUser.username, siteUser.password, authorities)
    }
}