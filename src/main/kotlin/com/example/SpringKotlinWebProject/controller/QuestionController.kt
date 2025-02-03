package com.example.SpringKotlinWebProject.controller



import com.example.SpringKotlinWebProject.Form.AnswerForm
import com.example.SpringKotlinWebProject.Form.QuestionForm
import com.example.SpringKotlinWebProject.model.Question
import com.example.SpringKotlinWebProject.model.SiteUser
import com.example.SpringKotlinWebProject.service.QuestionService
import com.example.SpringKotlinWebProject.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.security.Principal


@RequestMapping("/question")
@Controller
class QuestionController(
    private val questionService: QuestionService,
    private val userService: UserService,
) {

    @GetMapping("/list")
    fun list(model: Model, @RequestParam(value = "page", defaultValue = "0") page: Int,
             @RequestParam(value = "kw", defaultValue = "") kw:String): String {
        val paging = questionService.getList(page, kw)
        model.addAttribute("paging", paging)
        model.addAttribute("kw", kw)
        return "question_list"
    }

    @GetMapping(value = ["/detail/{id}"])
    fun detail(
        model: Model,
        @PathVariable("id") id: Int,
        answerForm: AnswerForm
    ): String {
        val question = questionService.getQuestion(id)
        model.addAttribute("question", question)
        return "question_detail"
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    fun create(
        model:Model,
        questionForm: QuestionForm,
    ): String{
        return "question_form"
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    fun questionCreate(
        model: Model,
        @Valid questionForm: QuestionForm, // 유효성 검증
        bindingResult: BindingResult,       // 검증 결과
        principal: Principal,
    ): String {
        // 1. 검증 실패 처리
        if (bindingResult.hasErrors()) {
            return "question_form"
        }

        // 2. 검증 성공 시 서비스 호출
        val siteuser: SiteUser = userService.getUser(principal.name)
        questionService.create(questionForm.subject, questionForm.content, siteuser)

        // 3. 질문 목록으로 리다이렉트
        return "redirect:/question/list"
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    fun questionModify(
        questionForm: QuestionForm,
        @PathVariable("id") id: Int,
        principal: Principal
    ): String {
        val question = questionService.getQuestion(id)

        if (question.author!!.username != principal.name) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.")
        }

        questionForm.subject = question.subject
        questionForm.content = question.content

        return "question_form"
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    fun questionModify(
        @Valid questionForm: QuestionForm,
        bindingResult: BindingResult,
        principal: Principal,
        @PathVariable("id") id: Int
    ): String {
        if (bindingResult.hasErrors()) {
            return "question_form"
        }

        val question = questionService.getQuestion(id)

        if (question.author!!.username != principal.name) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.")
        }

        questionService.modify(questionForm.subject, questionForm.content, question)

        return "redirect:/question/detail/$id"
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    fun questionDelete(
        principal: Principal,
        @PathVariable("id") id: Int
    ): String {
        val question = questionService.getQuestion(id)

        if (question.author!!.username != principal.name) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.")
        }

        questionService.delete(question)

        return "redirect:/"
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    fun questionVote(
        principal: Principal,
        @PathVariable("id") id: Int
    ): String {
        val question = questionService.getQuestion(id)
        val siteUser = userService.getUser(principal.name)

        questionService.vote(question, siteUser)

        return "redirect:/question/detail/$id"
    }

}