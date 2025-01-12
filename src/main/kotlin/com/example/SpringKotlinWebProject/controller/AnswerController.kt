package com.example.SpringKotlinWebProject.controller

import com.example.SpringKotlinWebProject.Form.AnswerForm
import com.example.SpringKotlinWebProject.Form.QuestionForm
import com.example.SpringKotlinWebProject.model.Question
import com.example.SpringKotlinWebProject.service.AnswerService
import com.example.SpringKotlinWebProject.service.QuestionService
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.ui.Model
import org.springframework.validation.BindingResult

@RequestMapping("/answer")
@Controller
class AnswerController(
    private val questionService: QuestionService,
    private val answerService: AnswerService
) {

    @PostMapping("/create/{id}")
    fun createAnswer(
        model: Model,
        @PathVariable("id") id: Int,
        @Valid answerForm: AnswerForm, // 유효성 검증
        bindingResult: BindingResult       // 검증 결과
    ): String {
        val question: Question = questionService.getQuestion(id)
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail"
        }

        answerService.create(question, answerForm.content)
        return String.format("redirect:/question/detail/%s", id)
    }
}