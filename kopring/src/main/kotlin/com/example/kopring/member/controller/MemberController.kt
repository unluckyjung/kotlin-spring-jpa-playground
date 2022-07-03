package com.example.kopring.member.controller

import com.example.kopring.member.dto.MemberTestDto
import com.example.kopring.member.dto.MemberValidationGroup
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.Min

@RestController
@RequestMapping("api/v1/member/")
//@Validated
class MemberController {
    @GetMapping("valid-get-controller")
    // @Valid는 그룹이 안잡혀있는것만 체크, 즉 전부 통과
    fun getMemberInfoValid(@Valid @RequestBody memberReq: MemberTestDto): String {
        return """
            name: ${memberReq.name}
            age: ${memberReq.age}
        """.trimIndent()
    }

    @GetMapping("validated-get-controller")
    fun getMemberInfoValidated(
        @Validated(MemberValidationGroup.AgeCheck::class)
        @RequestBody memberReq: MemberTestDto
    ): String {
        return """
            name: ${memberReq.name}
            age: ${memberReq.age}
        """.trimIndent()
    }

    @GetMapping("validated-get-param")
    fun getMemberInfoValidatedParam(
        // validation 안되는 @Min
        // class 단에 Validated 추가시 인터셉트 검증 작업 발생 -> 500에러 발생
        @Min(value = 20, message = "나이는 20살이상 이여야 합니다.")
        @RequestParam age: Int,
    ): String {
        return """
            age: $age
        """.trimIndent()
    }
}