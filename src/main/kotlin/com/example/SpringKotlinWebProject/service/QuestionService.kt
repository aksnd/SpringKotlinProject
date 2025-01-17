package com.example.SpringKotlinWebProject.service

import com.example.SpringKotlinWebProject.model.Question
import com.example.SpringKotlinWebProject.others.DataNotFoundException
import com.example.SpringKotlinWebProject.repository.QuestionRepository
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*


@Service
class QuestionService(private val questionRepository: QuestionRepository) {

    fun getList(page: Int): Page<Question> {
        val pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"))
        return questionRepository.findAll(pageable)
    }

    fun getQuestion(id: Int): Question {
        val question: Optional<Question> = questionRepository.findById(id.toLong())
        if (question.isPresent) {
            return question.get()
        } else {
            throw DataNotFoundException("question not found")
        }
    }

    fun create(subject: String, content: String) {
        val question = Question(
            subject = subject,
            content = content,
            createDate = LocalDateTime.now()
        )
        questionRepository.save(question)
    }
}
