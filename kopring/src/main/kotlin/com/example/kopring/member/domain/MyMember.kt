package com.example.kopring.member.domain

import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.time.ZonedDateTime
import javax.persistence.*

@Where(clause = "deleted_at is null")
@SQLDelete(sql = "UPDATE my_member SET deleted_at = NOW() WHERE member_id = ?")
@Table(name = "my_member")
@Entity
class MyMember(
    @Column(name = "member_name", nullable = false)
    val name: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    var team: Team?,

    deletedAt: ZonedDateTime? = null,

    @Column(name = "member_id", nullable = false)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
) {
    @Column(name = "deleted_at")
    var deletedAt: ZonedDateTime? = deletedAt
        protected set

    fun delete() {
        deletedAt = ZonedDateTime.now()
    }
}
