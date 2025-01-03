package com.example.SpringKotlinWebProject.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping


@Controller
class MainController {
    @GetMapping("/")
    fun root(): String {
        return "redirect:/question/list"
    }
}