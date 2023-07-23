package com.example.kopring

import com.example.kopring.member.domain.Member
import com.example.kopring.member.domain.MemberRepository
import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.stereotype.Service
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.UnexpectedRollbackException
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional


@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest
class TransactionRollbackTest(
    private val memberRepository: MemberRepository,
    private val transactionRollbackTestServiceParent: TransactionRollbackTestServiceParent,
) {
    @BeforeEach
    internal fun setUp() {
        memberRepository.deleteAllInBatch()
    }

    val parentMemberName = "parentGoodall"
    val childMemberName = "childUnluckyjung"

    val parentMember = Member(name = parentMemberName)
    val childMember = Member(name = childMemberName)


    @DisplayName("[REQUIRES_NEW] 자식 트랜잭션에서 예외가 발생하고, 컨트롤되지 않으면 둘다 롤백된다.")
    @Test
    fun rollbackTest1() {
        shouldThrowAny {
            transactionRollbackTestServiceParent.requiresNew(
                parentMember = parentMember,
                childMember = childMember,
                ex = IllegalArgumentException(),
            )
        }
        val result = memberRepository.findAll()
        result.size shouldBe 0
    }

    @DisplayName("[REQUIRES_NEW] 자식 트랜잭션에서 예외가 발생하고, 부모 트랜잭션에서 예외를 try-catch 처리시 자식만 롤백된다.")
    @Test
    fun rollbackTest2() {
        transactionRollbackTestServiceParent.requiresNewAndTryCatch(
            parentMember = parentMember,
            childMember = childMember,
            ex = IllegalArgumentException(),
        )
        val result = memberRepository.findAll()
        result.size shouldBe 1
        result[0].name shouldBe parentMemberName
    }

    @DisplayName("[REQUIRES_NEW] 자식 트랜잭션에서 예외가 발생하지 않으면, 둘다 커밋된다.")
    @Test
    fun rollbackTest3() {
        transactionRollbackTestServiceParent.requiresNew(
            parentMember = parentMember,
            childMember = childMember,
            ex = null,
        )
        val result = memberRepository.findAll()
        result.size shouldBe 2
    }

    @DisplayName("[REQUIRES_NEW] 자식 트랜잭션 호출 후, 부모 트랜잭션에서 예외가 발생하면, 자식의 작업만 커밋된다.")
    @Test
    fun rollbackTest31() {
        shouldThrowAny {
            transactionRollbackTestServiceParent.requiresNewAndThrowParentException(
                parentMember = parentMember,
                childMember = childMember,
                ex = IllegalArgumentException(),
            )
        }
        val result = memberRepository.findAll()
        result.size shouldBe 1
        result[0].name shouldBe childMemberName
    }

    @DisplayName("[REQUIRED] 하위 서비스에서 예외가 발생하고, 컨트롤되지 않으면 둘다 롤백된다.")
    @Test
    fun rollbackTest4() {
        shouldThrowAny {
            transactionRollbackTestServiceParent.required(
                parentMember = parentMember,
                childMember = childMember,
                ex = IllegalArgumentException(),
            )
        }
        val result = memberRepository.findAll()
        result.size shouldBe 0
    }

    @DisplayName("[REQUIRED] 하위 서비스에서 예외가 발생하고, 상위 서비스에서 try-catch 예외 처리를 해주어도 UnExpectedRollBackException 이 발생하며 전부 롤백된다.")
    @Test
    fun rollbackTest5() {
        shouldThrowExactly<UnexpectedRollbackException> {
            transactionRollbackTestServiceParent.requiredAndTryCatch(
                parentMember = parentMember,
                childMember = childMember,
                ex = IllegalArgumentException(),
            )
        }
        val result = memberRepository.findAll()
        result.size shouldBe 0
    }


    @DisplayName("[REQUIRED] 하위 서비스에서 예외가 발생하고, 하위 서비스에서 try-catch 예외 처리를 해주면 롤백이 진행되지 않는다.")
    @Test
    fun rollbackTest5_1() {
        transactionRollbackTestServiceParent.requiredAndTryCatchInChild(
            parentMember = parentMember,
            childMember = childMember,
            ex = IllegalArgumentException(),
        )
        val result = memberRepository.findAll()
        result.size shouldBe 2
    }

    @DisplayName("[REQUIRED] 하위 서비스에서 트랜잭션 어노테이션이 없을떄 예외가 발생했다면 롤백이 진행되지 않는다.")
    @Test
    fun rollbackTest5_2() {
        transactionRollbackTestServiceParent.requiredAndChildNoTransactionAnnotation(
            parentMember = parentMember,
            childMember = childMember,
            ex = IllegalArgumentException(),
        )
        val result = memberRepository.findAll()
        result.size shouldBe 2
    }

    @DisplayName("[REQUIRED] 하위 서비스에서 예외가 발생하지 않으면, 둘다 저장된다.")
    @Test
    fun rollbackTest6() {
        transactionRollbackTestServiceParent.required(
            parentMember = parentMember,
            childMember = childMember,
            ex = null,
        )
        val result = memberRepository.findAll()
        result.size shouldBe 2
    }
}

@Service
class TransactionRollbackTestServiceParent(
    private val memberRepository: MemberRepository,
    private val transactionRollbackTestServiceChild: TransactionRollbackTestServiceChild,
) {

    @Transactional
    fun requiresNew(parentMember: Member, childMember: Member, ex: Exception?) {
        memberRepository.save(parentMember)
        transactionRollbackTestServiceChild.requiresNewSave(childMember, ex)
    }

    @Transactional
    fun requiresNewAndTryCatch(parentMember: Member, childMember: Member, ex: Exception?) {
        memberRepository.save(parentMember)
        try {
            transactionRollbackTestServiceChild.requiresNewSave(childMember, ex)
        } catch (e: Exception) {
            println("Exception caught")
        }
    }

    @Transactional
    fun requiresNewAndThrowParentException(parentMember: Member, childMember: Member, ex: Exception?) {
        memberRepository.save(parentMember)
        transactionRollbackTestServiceChild.requiresNewSave(childMember, null)
        if (ex != null) {
            throw ex
        }
    }

    @Transactional
    fun required(parentMember: Member, childMember: Member, ex: Exception?) {
        memberRepository.save(parentMember)
        transactionRollbackTestServiceChild.requiredSave(childMember, ex)
    }

    @Transactional
    fun requiredAndTryCatch(parentMember: Member, childMember: Member, ex: Exception?) {
        memberRepository.save(parentMember)

        try {
            transactionRollbackTestServiceChild.requiredSave(childMember, ex)
        } catch (e: Exception) {
            println("Exception caught")
        }
        println("after try-catch...")

        memberRepository.save(parentMember)
    }

    @Transactional
    fun requiredAndTryCatchInChild(parentMember: Member, childMember: Member, ex: Exception?) {
        memberRepository.save(parentMember)

        transactionRollbackTestServiceChild.requiredSaveWithTryCatch(childMember, ex)

        memberRepository.save(parentMember)
    }


    @Transactional
    fun requiredAndChildNoTransactionAnnotation(parentMember: Member, childMember: Member, ex: Exception?) {
        memberRepository.save(parentMember)

        try {
            transactionRollbackTestServiceChild.requiredSaveNoTransactionAnnotation(childMember, ex)
        } catch (e: Exception) {
            println("Exception caught")
        }
        println("after try-catch...")

        memberRepository.save(parentMember)
    }
}

@Service
class TransactionRollbackTestServiceChild(
    private val memberRepository: MemberRepository,
) {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun requiresNewSave(member: Member, ex: Exception?) {
        memberRepository.save(member)

        if (ex != null) {
            throw ex
        }
    }

    @Transactional
    fun requiredSave(member: Member, ex: Exception?) {
        memberRepository.save(member)

        if (ex != null) {
            throw ex
        }
    }

    @Transactional
    fun requiredSaveWithTryCatch(member: Member, ex: Exception?) {
        try {
            memberRepository.save(member)
            if (ex != null) {
                throw ex
            }
        } catch (e: Exception) {
            println("exception throw")
        }
    }


    fun requiredSaveNoTransactionAnnotation(member: Member, ex: Exception?) {
        memberRepository.save(member)

        if (ex != null) {
            throw ex
        }
    }
}
