package com.example.SpringKotlinWebProject.repository

import com.example.SpringKotlinWebProject.model.Question
import org.springframework.data.jpa.repository.JpaRepository

interface QuestionRepository : JpaRepository<Question, Long>