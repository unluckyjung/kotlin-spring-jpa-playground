package com.example.kopring.member.domain

import com.example.kopring.BaseEntity
import javax.persistence.*

@Entity(name = "abstract_member")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
sealed class AbstractMember(
    val name: String,

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "team_id")
//    var team: Team? = null,

    @Column(name = "member_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
) : BaseEntity()

// 엔티티만 존재
// @Table(name = "my_member")
@Entity
@DiscriminatorValue("BLACK")
class BMember(name: String) : AbstractMember(name = name)

// 엔티티만 존재
// @Table(name = "my_member")
@Entity
@DiscriminatorValue("WHITE")
class WMember(name: String) : AbstractMember(name = name)
