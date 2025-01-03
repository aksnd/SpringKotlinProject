package com.example.SpringKotlinWebProject.repository

import com.example.SpringKotlinWebProject.model.Answer
import org.springframework.data.jpa.repository.JpaRepository

interface AnswerRepository : JpaRepository<Answer, Long>