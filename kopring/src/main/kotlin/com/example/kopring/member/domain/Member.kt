package com.example.kopring.member.domain

import com.example.kopring.BaseEntity
import java.time.ZonedDateTime
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Table(name = "member")
@Entity
class Member(
    @Column(name = "name")
    val name: String,

    createdAt: ZonedDateTime = ZonedDateTime.now(),

    @Column(name = "id", nullable = false)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
) : BaseEntity(createdAt = createdAt) {
    data class Request(
        @field: NotBlank(message = "이름은 공백으로 이루어져있을 수 없습니다.")
        val name: String,
    )

    data class Response(
        val id: Long,
        val name: String,
    )
}
