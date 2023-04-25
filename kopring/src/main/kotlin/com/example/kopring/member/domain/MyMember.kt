package com.example.kopring.member.domain

import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.time.ZonedDateTime
import javax.persistence.*

@Where(clause = "deleted_at is null")
@SQLDelete(sql = "UPDATE my_member SET deleted_at = NOW() WHERE id = ?")
@Table(name = "my_member")
@Entity
class MyMember(
    @Column(name = "name", nullable = false)
    val name: String,

    @OneToMany(mappedBy = "myMember", cascade = [CascadeType.ALL], orphanRemoval = true)
    val mids: MutableList<Mid> = mutableListOf(),

    deletedAt: ZonedDateTime? = null,

    @Column(name = "id", nullable = false)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
) {
    @Column(name = "deleted_at")
    var deletedAt: ZonedDateTime? = deletedAt
        protected set
}
