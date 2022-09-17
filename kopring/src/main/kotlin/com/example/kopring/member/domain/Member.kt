package com.example.kopring.member.domain

import com.example.kopring.BaseEntity
import java.time.ZonedDateTime
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
class Member(

    val name: String,
    createdAt: ZonedDateTime = ZonedDateTime.now(),


    info: Info = Info(age = 5, nickName = "default nickName"),

    @Column(name = "member_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

) : BaseEntity(createdAt = createdAt) {

    @Embedded
    var info: Info = info
        private set

    fun changeInfo(info: Info) {
        this.info = info
    }

    data class Request(
        @field: NotBlank(message = "이름은 공백으로 이루어져있을 수 없습니다.")
        val name: String
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Member

        if (name != other.name) return false
        if (info != other.info) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + info.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }
}

@Embeddable
data class Info(
    @Column(name = "age", nullable = false)
    val age: Int = 0,

    @Column(name = "nick_name", nullable = false)
    val nickName: String = "default"
)

