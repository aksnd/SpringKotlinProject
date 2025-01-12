package com.example.SpringKotlinWebProject.controller



import com.example.SpringKotlinWebProject.Form.AnswerForm
import com.example.SpringKotlinWebProject.Form.QuestionForm
import com.example.SpringKotlinWebProject.service.QuestionService
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*


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
    fun detail(
        model: Model,
        @PathVariable("id") id: Int,
        answerForm: AnswerForm
    ): String {
        val question = questionService.getQuestion(id)
        model.addAttribute("question", question)
        return "question_detail"
    }
    @GetMapping("/create")
    fun create(
        model:Model,
        questionForm: QuestionForm,
    ): String{
        return "question_form"
    }

    @PostMapping("/create")
    fun questionCreate(
        model: Model,
        @Valid questionForm: QuestionForm, // 유효성 검증
        bindingResult: BindingResult       // 검증 결과
    ): String {
        // 1. 검증 실패 처리
        if (bindingResult.hasErrors()) {
            return "question_form"
        }

        // 2. 검증 성공 시 서비스 호출
        questionService.create(questionForm.subject, questionForm.content)

        // 3. 질문 목록으로 리다이렉트
        return "redirect:/question/list"
    }
}