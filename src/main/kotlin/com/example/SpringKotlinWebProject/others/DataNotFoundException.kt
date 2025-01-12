package com.example.SpringKotlinWebProject.others

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity not found")
class DataNotFoundException(message: String?) : RuntimeException(message) {
    companion object {
        private const val serialVersionUID = 1L
    }
}
//쉽게 말해 404 반환