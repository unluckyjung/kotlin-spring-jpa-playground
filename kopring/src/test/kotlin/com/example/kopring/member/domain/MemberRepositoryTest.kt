package com.example.kopring.member.domain

import com.example.kopring.common.support.jpa.findByIdOrThrow
import com.example.kopring.test.RepositoryTest
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.spyk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

@RepositoryTest
class MemberRepositoryTest(
    private val memberRepository: MemberRepository
) {

    lateinit var member: Member

    @BeforeEach
    internal fun setUp() {
        member = memberRepository.save(
            Member("goodall")
        )
    }

    @DisplayName("멤버가 최초로 저장되면, id로 조회했을때 조회된다.")
    @Test
    fun memberGetByIdTest() {
        val member1 = memberRepository.findByMemberId(member.id)
        member1 shouldBe member
    }

    @DisplayName("존재하지 않는 id로 조회하면, 예외가 발생한다.")
    @Test
    fun memberGetByIdFailTest() {
        assertThrows<NoSuchElementException> {
            memberRepository.findByMemberId(100L)
        }
    }

    @DisplayName("존재하지 않는 id로 조회하면, 예외가 발생한다2")
    @Test
    fun findByIdOrThrowTest() {
        shouldThrowExactly<IllegalArgumentException> {
            memberRepository.findByIdOrThrow(100L)
        }
    }

    @DisplayName("외부에서 createdAt 시간을 주입해주면, 현재 시간을 무시하고 주입받은 시간으로 createdAt 시간이 설정된다.")
    @Test
    fun timeDependencyInjectionTest() {
        val inputKoreaTime = ZonedDateTime.of(
            LocalDateTime.of(2222, 2, 2, 2, 2), ZoneId.of("Asia/Seoul")
        )
        val member1 = Member(
            "jys",
            createdAt = inputKoreaTime
        )

        member1.createdAt shouldNotBe ZonedDateTime.now()
        member1.createdAt shouldBe inputKoreaTime
    }

    @DisplayName("prePersist() 메소드를 spyk 를 이용하여 mocking 하면, 영속화가 되어도 모킹한 시간이 유지된다.")
    @Test
    fun spyMockTest() {
        val inputKoreaTime = ZonedDateTime.of(
            LocalDateTime.of(2222, 2, 2, 2, 2), ZoneId.of("Asia/Seoul")
        )

        val spyMember: Member = spyk(Member("jys")) {
            every { prePersist() } answers {
                createdAt = inputKoreaTime
            }
        }
        val savedMember = memberRepository.save(spyMember)   // inputKoreaTime이 아닌, now()로 시간이 저장된다.

        savedMember.createdAt shouldBe inputKoreaTime
    }

    @DisplayName("ZonedDateTime.now()를 static mocking 이용해 시간을 더해주면, 더해준 시간으로 반환된다.")
    @ParameterizedTest
    @ValueSource(longs = [1, 2, 3, 4])
    fun zoneDateTimeMockingTest(plusDay: Long) {
        val inputKoreaTime = ZonedDateTime.of(
            LocalDateTime.of(2222, 2, 2, 2, 2), ZoneId.of("Asia/Seoul")
        )

        mockkStatic(ZonedDateTime::class)
        every {
            ZonedDateTime.now()
        }.returns(inputKoreaTime.plusDays(plusDay))
        val savedMember = memberRepository.save(Member("unluckyjung"))

        savedMember.createdAt shouldBe inputKoreaTime.plusDays(plusDay)
    }
}
