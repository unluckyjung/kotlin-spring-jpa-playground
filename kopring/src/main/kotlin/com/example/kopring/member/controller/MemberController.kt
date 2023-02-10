package com.example.kopring.member.controller

import com.example.kopring.member.domain.Member
import com.example.kopring.member.service.MemberService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RequestMapping("api/v1/member")
@RestController
class MemberController(
    private val memberService: MemberService,
) {

    @GetMapping("/{id}")
    fun find(@PathVariable id: Long): Member.Response {
        return memberService.find(id = id)
    }

    @PostMapping
    fun save(@RequestBody @Valid request: Member.Request): Member.Response {
        return memberService.save(request)
    }
}
