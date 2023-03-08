//package com.example.kopring.member
//
//import com.example.kopring.common.support.jpa.findByIdOrThrow
//import com.example.kopring.member.domain.MyMember
//import com.example.kopring.member.domain.Team
//import com.example.kopring.member.domain.TeamRepository
//import org.slf4j.LoggerFactory
//import org.springframework.boot.ApplicationArguments
//import org.springframework.boot.ApplicationRunner
//import org.springframework.core.annotation.Order
//import org.springframework.stereotype.Component
//import org.springframework.transaction.annotation.Transactional
//
//@Order(1)
//@Component
//class InitLoader(
//    private val teamRepository: TeamRepository,
//) : ApplicationRunner {
//
//    @Transactional
//    override fun run(args: ApplicationArguments?) {
//        Team("team1").apply {
//            // 연관관계의 주인인 MyMember 에 바로 team 을 넣어주면서 삽입 (team 을 NotNull 하게 관리가능)
//            this.addMember(MyMember("member1", this))
//            this.addMember(MyMember("member2", this))
//        }.let(teamRepository::save)
//
//        Team("team2").apply {
//            this.addAllMembers(listOf(MyMember("member3", this), MyMember("member4", this)))
//        }.let(teamRepository::save)
//
//        // MyMember.team 을 nullable 하면 객체 생성 타이밍을 다르게 가져갈 수 있다.
//        val members = mutableListOf(MyMember("member5", null), MyMember("member6", null))
//        Team("team3").apply {
//            this.addAllMembers(members)
//        }.let(teamRepository::save)
//
//        Team("team4").apply {
//            this.addAllMembers(listOf(MyMember("member7", this), MyMember("member8", this)))
//        }.let(teamRepository::save)
//
//        Team("team5").apply {
//            this.addAllMembers(listOf(MyMember("member9", this), MyMember("member10", this)))
//        }.let(teamRepository::save)
//    }
//}
//
//@Order(2)
//@Component
//class InitLoader2(
//    private val teamRepository: TeamRepository,
//) : ApplicationRunner {
//    @Transactional
//    override fun run(args: ApplicationArguments?) {
//        val team = teamRepository.findByIdOrThrow(1)
//
//        // cascade 를 이용한 삭제
//        team.members.removeAt(1)
//    }
//}
//
//@Order(3)
//@Component
//class InitLoader3(
//    private val teamRepository: TeamRepository,
//) : ApplicationRunner {
//
//    @Transactional(readOnly = true)
//    override fun run(args: ApplicationArguments?) {
//        logger.info("lazyLoading Check lazy ======================")
//        val team = teamRepository.findByIdOrThrow(1) // team 만 select 하고 member join 쿼리 안나감.
//        logger.info("lazyLoading Check: loading =====================")
//
//        // member join query 발생.
//        logger.info("members size : ${team.members.size}")
//    }
//
//    companion object {
//        val logger = LoggerFactory.getLogger(this::class.java)
//    }
//}
//
//@Order(4)
//@Component
//class InitLoader4(
//    private val teamRepository: TeamRepository,
//) : ApplicationRunner {
//
//    @Transactional
//    override fun run(args: ApplicationArguments?) {
//        val team = teamRepository.findByIdOrThrow(3)
//        team.delete()
//    }
//}
//
//@Order(5)
//@Component
//class InitLoader5(
//    private val teamRepository: TeamRepository,
//) : ApplicationRunner {
//
//    @Transactional
//    override fun run(args: ApplicationArguments?) {
//        val team = teamRepository.findByIdOrThrow(5)
//        team.apply {
//            addMember(MyMember("goodall", this))
//            addMember(MyMember("unluckyjung", this))
//        }
//    }
//}
