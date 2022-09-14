package com.example.kopring.dummy

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/v1/dummy")
@RestController
class DummyController {

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    fun get(@RequestBody dummyRequest: DummyRequest): String {
        return "request name is: ${dummyRequest.name}"
    }
}

data class DummyRequest(
    val name: String,
    val age: Int,
)
