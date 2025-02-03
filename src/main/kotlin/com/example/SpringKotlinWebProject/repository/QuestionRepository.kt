package com.example.SpringKotlinWebProject.repository

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.SpringKotlinWebProject.model.Question
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository

interface QuestionRepository : JpaRepository<Question, Long>{
    fun findBySubject(subject: String): Question?
    fun findBySubjectAndContent(subject: String, content: String): Question?
    fun findBySubjectLike(subject: String): List<Question>
    override fun findAll(pageable: Pageable): Page<Question>
    fun findAll(spec: Specification<Question>, pageable: Pageable): Page<Question>

}

