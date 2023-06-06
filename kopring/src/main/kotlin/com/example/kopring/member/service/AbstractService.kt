package com.example.kopring.member.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

abstract class AbstractService(
    private val memberService: MemberService,
) {
    fun getHashCode(): Int {
        return memberService.hashCode()
    }

    abstract fun abstractFun()
}

@Service
class AbstractServiceImpl(
    memberService: MemberService,
) : AbstractService(
    memberService = memberService,
) {
    override fun abstractFun() {
        println("constructor abstractFun")
    }
}

@Service
abstract class AbstractServiceUseAutoWired {

    // autowired 를 이용하면, 구현체 클래스에서 또 의존성 주입을 받아줄필요가 없어진다.
    @Autowired
    private lateinit var memberService: MemberService

    fun getHashCode(): Int {
        return memberService.hashCode()
    }

    abstract fun abstractFun()
}

@Service
class AbstractServiceUseAutoWiredImpl : AbstractServiceUseAutoWired() {
    override fun abstractFun() {
        println("autowired abstractFun")
    }
}


@Service
class CallerService(
    private val abstractServiceImpl: AbstractServiceImpl,
    private val abstractServiceUseAutoWiredImpl: AbstractServiceUseAutoWiredImpl
) {
    fun runConstructor(){
        println("runConstructor")
        println(abstractServiceImpl.getHashCode())
        abstractServiceImpl.abstractFun()
    }

    fun runAutowired(){
        println("runAutowired")
        println(abstractServiceUseAutoWiredImpl.getHashCode())
        abstractServiceUseAutoWiredImpl.abstractFun()
    }
}
