package com.example.kopring.member.domain

import com.example.kopring.BaseEntity
import java.time.ZonedDateTime
import javax.persistence.*
import javax.validation.constraints.NotBlank


@Table(name = "member", indexes = [Index(name = "idx__name_priority", columnList = "name,priority")])
@Entity
class Member(
    name: String,
    nickName: String = "123",

    createdAt: ZonedDateTime = ZonedDateTime.now(),

    @Column(name = "priority", nullable = false)
    val priority: Int = 0,

    @Column(name = "id", nullable = false)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
) : BaseEntity(createdAt = createdAt) {

    @Column(name = "name", nullable = false)
    var name = name
        protected set

    @Column(name = "nickName", nullable = false)
    var nickName = nickName
        protected set

    fun changeName(name: String) {
        this.name = name
    }

    data class Request(
        @field: NotBlank(message = "이름은 공백으로 이루어져있을 수 없습니다.")
        val name: String,
    )

    data class Response(
        val id: Long,
        val name: String,
    )
}
