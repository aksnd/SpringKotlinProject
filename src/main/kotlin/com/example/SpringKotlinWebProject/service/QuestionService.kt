package com.example.SpringKotlinWebProject.service

import com.example.SpringKotlinWebProject.model.Answer
import com.example.SpringKotlinWebProject.model.Question
import com.example.SpringKotlinWebProject.model.SiteUser
import com.example.SpringKotlinWebProject.others.DataNotFoundException
import com.example.SpringKotlinWebProject.repository.QuestionRepository
import jakarta.persistence.criteria.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*


@Service
class QuestionService(private val questionRepository: QuestionRepository) {

    private fun search(kw: String): Specification<Question> {
        return Specification { q: Root<Question>, query: CriteriaQuery<*>?, cb: CriteriaBuilder ->
            query?.distinct(true) // 중복 제거

            val u1: Join<Question, SiteUser> = q.join("author", JoinType.LEFT)
            val a: Join<Question, Answer> = q.join("answerList", JoinType.LEFT)
            val u2: Join<Answer, SiteUser> = a.join("author", JoinType.LEFT)

            cb.or(
                cb.like(q.get("subject"), "%$kw%"),  // 제목
                cb.like(q.get("content"), "%$kw%"),  // 내용
                cb.like(u1.get("username"), "%$kw%"),  // 질문 작성자
                cb.like(a.get("content"), "%$kw%"),  // 답변 내용
                cb.like(u2.get("username"), "%$kw%") // 답변 작성자
            )
        }
    }



    fun getList(page: Int, kw: String): Page<Question> {
        val pageable: Pageable = PageRequest.of(page, 10, Sort.by(Sort.Order.desc("createDate")))
        val spec: Specification<Question> = search(kw)
        return questionRepository.findAll(spec, pageable)
    }

    fun getQuestion(id: Int): Question {
        val question: Optional<Question> = questionRepository.findById(id.toLong())
        if (question.isPresent) {
            return question.get()
        } else {
            throw DataNotFoundException("question not found")
        }
    }

    fun create(subject: String, content: String, user:SiteUser) {
        val question = Question(
            subject = subject,
            content = content,
            createDate = LocalDateTime.now(),
            author = user,
            voter = mutableSetOf()
        )
        questionRepository.save(question)
    }
    fun modify(subject: String, content: String, question:Question){
        question.subject = subject
        question.content = content
        question.modifyDate = LocalDateTime.now()

        questionRepository.save(question)
    }

    fun delete(question: Question) {
        questionRepository.delete(question)
    }

    fun vote(question: Question, siteUser: SiteUser) {
        if (siteUser !in question.voter) {
            question.voter = question.voter.toMutableSet().apply { add(siteUser) }
            questionRepository.save(question)
        }
    }


}
