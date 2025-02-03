package com.example.SpringKotlinWebProject.controller

import com.example.SpringKotlinWebProject.Form.AnswerForm
import com.example.SpringKotlinWebProject.model.Question
import com.example.SpringKotlinWebProject.model.SiteUser
import com.example.SpringKotlinWebProject.service.AnswerService
import com.example.SpringKotlinWebProject.service.QuestionService
import com.example.SpringKotlinWebProject.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.server.ResponseStatusException
import java.security.Principal

@RequestMapping("/answer")
@Controller
class AnswerController(
    private val questionService: QuestionService,
    private val answerService: AnswerService,
    private val userService: UserService
) {

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    fun createAnswer(
        model: Model,
        @PathVariable("id") id: Int,
        @Valid answerForm: AnswerForm, // 유효성 검증
        bindingResult: BindingResult,       // 검증 결과
        principal: Principal
    ): String {
        val question: Question = questionService.getQuestion(id)
        val siteuser: SiteUser = userService.getUser(principal.name)
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail"
        }

        answerService.create(question, answerForm.content, siteuser)
        return String.format("redirect:/question/detail/%s", id)
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    fun answerModify(
        answerForm: AnswerForm,
        @PathVariable("id") id: Int,
        principal: Principal
    ): String {
        val answer = answerService.getAnswer(id)

        if (answer.author.username != principal.name) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.")
        }

        answerForm.content = answer.content

        return "answer_form"
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    fun answerModify(
        @Valid answerForm: AnswerForm,
        bindingResult: BindingResult,
        @PathVariable("id") id: Int,
        principal: Principal
    ): String {
        if (bindingResult.hasErrors()) {
            return "answer_form"
        }

        val answer = answerService.getAnswer(id)

        if (answer.author.username != principal.name) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.")
        }

        answerService.modify(answerForm.content, answer)

        return "redirect:/question/detail/${answer.question!!.id}"
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    fun answerDelete(
        principal: Principal,
        @PathVariable("id") id: Int
    ): String {
        val answer = answerService.getAnswer(id)

        if (answer.author.username != principal.name) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.")
        }

        answerService.delete(answer)

        return "redirect:/question/detail/${answer.question!!.id}"
    }
}