package com.example.kopring.basic.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RequestMapping("/api/v1/basic")
@RestController
class BasicController {

    @GetMapping("hello")
    fun hello(@RequestParam name: String): String {
        return "hello swagger by $name"
    }
}
