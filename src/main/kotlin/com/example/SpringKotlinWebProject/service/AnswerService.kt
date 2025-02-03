package com.example.SpringKotlinWebProject.service

import com.example.SpringKotlinWebProject.model.Answer
import com.example.SpringKotlinWebProject.model.Question
import com.example.SpringKotlinWebProject.model.SiteUser
import com.example.SpringKotlinWebProject.others.DataNotFoundException
import com.example.SpringKotlinWebProject.repository.AnswerRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*


@Service
class AnswerService(private val answerRepository: AnswerRepository)  {
    fun create(question: Question, content: String, user: SiteUser) {
        val answer = Answer(
            question = question,
            content = content,
            createDate = LocalDateTime.now(),
            author = user,
        )
        answerRepository.save(answer)
    }

    fun getAnswer(id: Int): Answer {
        val answer: Optional<Answer> = answerRepository.findById(id.toLong())
        if (answer.isPresent) {
            return answer.get()
        } else {
            throw DataNotFoundException("answer not found")
        }
    }
    fun modify(content: String, answer: Answer){
        answer.content = content
        answer.modifyDate = LocalDateTime.now()

        answerRepository.save(answer)
    }

    fun delete(answer: Answer) {
        answerRepository.delete(answer)
    }
}




