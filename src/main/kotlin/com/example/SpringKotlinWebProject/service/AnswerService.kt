package com.example.SpringKotlinWebProject.service

import com.example.SpringKotlinWebProject.model.Answer
import com.example.SpringKotlinWebProject.model.Question
import com.example.SpringKotlinWebProject.repository.AnswerRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AnswerService(private val answerRepository: AnswerRepository)  {
    fun create(question: Question, content: String) {
        val answer = Answer(
            question = question,
            content = content,
            createDate = LocalDateTime.now()
        )
        answerRepository.save(answer)
    }
}




