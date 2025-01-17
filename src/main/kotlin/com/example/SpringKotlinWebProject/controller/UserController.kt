package com.example.SpringKotlinWebProject.controller

import com.example.SpringKotlinWebProject.Form.UserCreateForm
import com.example.SpringKotlinWebProject.service.UserService
import jakarta.validation.Valid
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {

    @GetMapping("/signup")
    fun signup(userCreateForm: UserCreateForm): String {
        return "signup_form"
    }

    @PostMapping("/signup")
    fun signup(
        @Valid userCreateForm: UserCreateForm,
        bindingResult: BindingResult
    ): String {
        if (bindingResult.hasErrors()) { //form 유효성 검사
            return "signup_form"
        }

        if (userCreateForm.password1 != userCreateForm.password2) {
            bindingResult.rejectValue(
                "password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다."
            )
            return "signup_form"
        }
        try {
            userService.create(
                userCreateForm.username,
                userCreateForm.email,
                userCreateForm.password1
            )
        } catch (e: DataIntegrityViolationException) {
            e.printStackTrace()
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.")
            return "signup_form"
        } catch (e: Exception) {
            e.printStackTrace()
            bindingResult.reject("signupFailed", e.message ?: "알 수 없는 오류가 발생했습니다.")
            return "signup_form"
        }


        return "redirect:/"
    }

    @GetMapping("/login")
    fun login(): String {
        return "login_form"
    }
}
