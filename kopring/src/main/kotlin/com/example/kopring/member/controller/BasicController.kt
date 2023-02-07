package com.example.kopring.member.controller

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1")
class BasicController {

    @GetMapping("/get-param")
    fun getController1(
        @RequestParam(name = "age") age: Int,
        @RequestParam(name = "name", defaultValue = "defaultName", required = false) name: String,
    ): String {
        return "name: $name"
    }

    @GetMapping("/get-model")
    fun getController2(
        @ModelAttribute req: ModelDto
    ): String {
        return "name: ${req.name}"
    }

    @GetMapping("/wrong-get-model")
    fun wrongGetController2(
        @ModelAttribute req: WrongModelDto
    ): String {
        // name 에 null 이 명시적으로 차버림. (name 은 NotNull type)
        return "name: ${req.name}"
    }

    @GetMapping("/wrong-get-model-with-enum")
    fun wrongGetController3(
        @ModelAttribute req: WrongModelDtoWithEnum
    ): String {
        // name 에 null 이 명시적으로 차버림. (name 은 NotNull type)
        return "name: ${req.enum}"
    }
}

class ModelDto(
    val age: Int,
    name: String?,
) {
    val name = name ?: "defaultName"
}

class WrongModelDto(
    val age: Int,
    val name: String = "defaultName",
)

class WrongModelDtoWithEnum(
    val age: Int,
    val enum: MyEnum?,
)

enum class MyEnum {
    GOODALL,
    YOON_SUNG,
    ;
}
