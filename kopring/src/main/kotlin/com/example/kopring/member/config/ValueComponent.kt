package com.example.kopring.member.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class ValueComponent {

    @Value("\${sampleValue.name}")
    lateinit var name: String

    @Value("\${sampleValue.age}")
    var age: Int = -1

    @Value("\${sampleValue.bookList}")
    lateinit var bookList: List<String>
}