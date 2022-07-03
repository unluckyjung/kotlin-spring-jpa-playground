package com.example.kopring.member.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.PositiveOrZero

data class MemberTestDto(
    @field:NotBlank(groups = [MemberValidationGroup.NameCheck::class])
    val name: String,

    @field:PositiveOrZero(groups = [MemberValidationGroup.AgeCheck::class])
    val age: Int,
)