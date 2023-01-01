package com.example.kopring.member.domain

import javax.persistence.CascadeType
import javax.persistence.Embeddable
import javax.persistence.OneToMany

@Embeddable
class MyMembers(
    @OneToMany(mappedBy = "team", cascade = [CascadeType.ALL], orphanRemoval = true)
    val members: MutableList<MyMember> = mutableListOf(),
) {
    fun addMember(member: MyMember) {
        members.add(member)
    }

    fun addAllMember(members: List<MyMember>) {
        members.forEach {
            addMember(it)
        }
    }
}
