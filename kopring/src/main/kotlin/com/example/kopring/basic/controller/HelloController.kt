package com.example.kopring.basic.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class HelloController {

    @GetMapping("query-param")
    fun hello(
        @RequestParam(required = false, defaultValue = "") cursorValue: Int,
        @RequestParam(required = false, defaultValue = "") cursorUuid: String,
    ): String {
        cursorUuid.isBlank()
        return """
            cursorValue: $cursorValue
            cursorUuid: $cursorUuid
        """.trimIndent()
    }
}
