package com.example.SpringKotlinWebProject.service

import com.example.SpringKotlinWebProject.model.Question
import com.example.SpringKotlinWebProject.others.DataNotFoundException
import com.example.SpringKotlinWebProject.repository.QuestionRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class QuestionService(private val questionRepository: QuestionRepository) {

    fun getList(): List<Question> {
        return questionRepository.findAll()
    }

    fun getQuestion(id: Int): Question {
        val question: Optional<Question> = questionRepository.findById(id.toLong())
        if (question.isPresent) {
            return question.get()
        } else {
            throw DataNotFoundException("question not found")
        }
    }
}
