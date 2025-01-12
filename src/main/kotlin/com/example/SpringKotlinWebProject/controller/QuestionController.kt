package com.example.SpringKotlinWebProject.controller



import com.example.SpringKotlinWebProject.service.QuestionService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping


@RequestMapping("/question")
@Controller
class QuestionController(private val questionService: QuestionService) {

    @GetMapping("/list")
    fun list(model: Model): String {
        val questionList = questionService.getList()
        model.addAttribute("questionList", questionList)
        return "question_list"
    }

    @GetMapping(value = ["/detail/{id}"])
    fun detail(model: Model, @PathVariable("id") id: Int): String {
        val question = questionService.getQuestion(id)
        model.addAttribute("question", question)
        return "question_detail"
    }
}