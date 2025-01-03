package com.example.SpringKotlinWebProject.controller

import com.example.SpringKotlinWebProject.repository.QuestionRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class QuestionController(private val questionRepository: QuestionRepository) {
    @GetMapping("/question/list")
    fun list(model: Model): String {
        val questionList = questionRepository.findAll()
        model.addAttribute("questionList", questionList)
        return "question_list"
    }
}