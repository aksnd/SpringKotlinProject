package com.example.SpringKotlinWebProject.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Question(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(length = 200)
    var subject: String = "",

    @Column(columnDefinition = "TEXT")
    var content: String = "",

    var createDate: LocalDateTime = LocalDateTime.now(),

    var modifyDate: LocalDateTime = LocalDateTime.now(),

    @ManyToOne
    var author: SiteUser?,

    @ManyToMany
    var voter: Set<SiteUser>,


    @OneToMany(mappedBy = "question", cascade = [CascadeType.REMOVE])
    var answerList: List<Answer> = mutableListOf()
)