package com.example.SpringKotlinWebProject.controller


import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class MainController {
    @GetMapping("/sbb")
    @ResponseBody
    fun index():String {
        return "안녕하세요 ssbsfd 오신 것을 환영합니다."
    }
}