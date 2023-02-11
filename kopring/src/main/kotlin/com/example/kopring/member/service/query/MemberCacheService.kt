package com.example.kopring.member.service.query

import com.example.kopring.member.domain.Member
import com.example.kopring.member.service.MemberService
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class MemberCacheService(
    private val memberService: MemberService,
) {
    @Cacheable(value = ["cacheTest"], key = "#id")
    fun findByUseCache(id: Long): Member.Response {
        return memberService.find(id)
    }

    fun findByUseNoCache(id: Long): Member.Response {
        return memberService.find(id)
    }
}
