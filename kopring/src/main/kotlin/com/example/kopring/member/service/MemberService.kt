package com.example.kopring.member.service

import com.example.kopring.member.dto.MemberTestDto
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import javax.validation.Valid

@Validated
@Service
class MemberService {
    // 500 에러
    fun validateTest(@Valid memberReq: MemberTestDto): String {
        return """
            name: ${memberReq.name}
            age: ${memberReq.age}
            nickName: ${memberReq.nickName}
        """.trimIndent()
    }
}