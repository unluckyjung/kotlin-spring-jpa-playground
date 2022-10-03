package com.example.kopring.member.domain

import javax.persistence.CascadeType
import javax.persistence.Embeddable
import javax.persistence.JoinColumn
import javax.persistence.OneToMany

@Embeddable
class Members(
    // 연관관계 주인은 MyMember(fk team_id 소유)
    @JoinColumn(name = "team_id") // OneToMany 단방향
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    val members: MutableSet<AbstractMember>, // 생성자 주입이지만, null 가능성이 열려있는줄 알았지만 문제 발생 x
) {
    fun add(member: AbstractMember) {
        members.add(member)
        // member.team = this
    }

    // all open 붙여서 private 불가
//    var test: String = "abc"
//        private set
}