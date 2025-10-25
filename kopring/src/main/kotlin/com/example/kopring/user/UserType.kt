package com.example.kopring.user

import com.example.kopring.member.domain.MemberRepository
import com.example.kopring.member.service.MemberService
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

enum class UserType {
    A_USER,
    B_USER,
    C_USER;

    private lateinit var userService: UserService

    private fun settingService(userService: UserService) {
        this.userService = userService
    }

    fun run(): String {
        if (::userService.isInitialized) {
            return this.userService.run("goodall")
        } else {
            throw IllegalStateException("설정안된 Service 가 있습니다. ${this.name}")
        }
    }

    @Component
    class UserServiceInjector(
        private val aUserService: AUserService,
        private val bUserService: BUserService,
    ) {

        @PostConstruct
        fun postConstruct() {
            A_USER.settingService(aUserService)
            B_USER.settingService(bUserService)
        }
    }
}

interface UserService {
    fun run(string: String): String
}

@Service
class AUserService(
    private val memberRepository: MemberRepository,
) : UserService {

    override fun run(string: String): String {
        return "AUserService $string"
    }
}

@Service
class BUserService(
    private val memberService: MemberService,
) : UserService {

    override fun run(string: String): String {
        return "BUserService $string"
    }
}
