package com.example.kopring.member.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.PositiveOrZero
import javax.validation.constraints.Size

data class MemberTestDto(
    @field:NotBlank(groups = [MemberValidationGroup.NameCheck::class])
    val name: String,

    @field:PositiveOrZero(groups = [MemberValidationGroup.AgeCheck::class])
    val age: Int,

    @field:Size(max = 10)
    val nickName: String? = null
)