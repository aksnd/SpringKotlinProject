package com.example.SpringKotlinWebProject.Form

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

data class QuestionForm(
    @field:NotEmpty(message = "제목은 필수항목입니다.")
    @field:Size(max = 200)
    var subject: String = "",

    @field:NotEmpty(message = "내용은 필수항목입니다.")
    var content: String = ""
)