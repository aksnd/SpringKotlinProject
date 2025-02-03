package com.example.SpringKotlinWebProject.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Answer(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(columnDefinition = "TEXT")
    var content: String = "",

    var createDate: LocalDateTime = LocalDateTime.now(),

    var modifyDate: LocalDateTime = LocalDateTime.now(),

    @ManyToOne
    var author: SiteUser,

    @ManyToOne
    var question: Question? = null
)