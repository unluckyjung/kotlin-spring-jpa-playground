package com.example.kopring.member.service

import com.example.kopring.member.domain.MyMember
import com.example.kopring.member.domain.MyMemberRepository
import com.example.kopring.member.domain.Team
import com.example.kopring.member.domain.TeamRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class InitLoader(
    private val teamRepository: TeamRepository,
    private val myMemberRepository: MyMemberRepository,
) : CommandLineRunner {

    @Transactional
    override fun run(vararg args: String?) {

        // 일반적으로 둘다 저장하는 방식
        // addMember 안의 편의메서드를 이용해 member 에 team 을 설정해준다.
        // team 안의 memberList 에 member 들도 채워준다. (member 도 memberRepository.save 를 호출하지않고 한번에 저장하기 위함)
        Team("team1").apply {
            this.addMember(MyMember("member1"))
            this.addMember(MyMember("member2"))
        }.let(teamRepository::save)

        onlyTeamSaveCase()
        errorCase()
        twiceSaveCase()
        nullTeamFkCase()
    }

    private fun onlyTeamSaveCase() {
        val slaveTeam = Team("slaveTeam1")

        MyMember("ownerMember1").run {
            // member 쪽에서는 team 을 세팅, 이때 team 은 저장되지 않은 순수 객체
            // Member 역시 순수 객체이다.
            this.team = slaveTeam
        }

        // slaveTeam1 만 저장되고, ownerMember1 는 저장 되지 않는다.
        // ownerMember1 는 저장되는 과정이 전혀 없었기 때문이다. (memberList is Empty)
        teamRepository.save(slaveTeam)
    }

    private fun errorCase() {
        val slaveTeam = Team("slaveTeam2")
        val ownerMember = MyMember("ownerMember2").apply {
            this.team = slaveTeam
        }

        // ownerMember2 저장을 시도하면 에러가 난다. 세팅해준 team 이 순수 객체라 pk 를 아직 가지고 있지 않기 때문.
//        myMemberRepository.save(ownerMember)
    }

    private fun twiceSaveCase() {
        // team 을 일단 저장한다.
        val slaveTeam3 = teamRepository.save(Team("slaveTeam3"))

        // team 에 id가 있어 연관관계 매핑과 member 저장까지 정상적으로 저장된다.
        MyMember("ownerMember3").apply {
            // slaveTeam3 이 영속화된 객체라서 fk 가 제대로 설정된다.
            this.team = slaveTeam3
        }.let(myMemberRepository::save)
    }

    private fun nullTeamFkCase() {
        val ownerMember4 = myMemberRepository.save(MyMember("ownerMember4"))
        val slaveTeam4 = Team("slaveTeam4")

        slaveTeam4.apply {
            // 연관관계 주인이 아닌쪽에서 List 에 저장만 한다.
            // 영속화된 ownerMember4 이지만, fk 가 아닌쪽에서만 세팅했기 때문에 ownerMember4 의 team_id 는 여전히 Null 이다.
            this.members.add(ownerMember4)
        }.let(teamRepository::save)
    }
}

