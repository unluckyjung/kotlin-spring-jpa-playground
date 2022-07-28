package com.example.kopring.member.domain

import com.example.kopring.BaseEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.time.ZonedDateTime
import javax.persistence.* // ktlint-disable no-wildcard-imports
import javax.validation.constraints.NotBlank

@Where(clause = "deleted_at is null")
@SQLDelete(sql = "UPDATE member SET deleted_at = NOW() WHERE member_id = ?")
@Entity
class Member(
    val name: String,
    createdAt: ZonedDateTime = ZonedDateTime.now(),

    @Column(name = "deleted_at")
    var deletedAt: ZonedDateTime? = null,

//    @Column(name = "deleted")
//    var deleted: Boolean = false,

    @Column(name = "member_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
) : BaseEntity(createdAt = createdAt) {

    fun delete() {
        deletedAt = ZonedDateTime.now()
    }

    data class Request(
        @field: NotBlank(message = "이름은 공백으로 이루어져있을 수 없습니다.")
        val name: String
    )
}
