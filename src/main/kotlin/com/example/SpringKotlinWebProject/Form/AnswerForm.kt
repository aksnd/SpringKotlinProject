package com.example.SpringKotlinWebProject.Form

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

data class AnswerForm(
    @field:NotEmpty(message = "내용은 필수항목입니다.")
    var content: String = ""
)