package com.example.kopring.mapstruct

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class MapStructTest {
    @DisplayName("필드가 완전히 같다면 특별한 구현이 없어도 된다.")
    @Test
    fun mapStructTest1() {
        val goodallDto = GoodallDto(name = "yoonsung", age = 32)
        val entity = GoodallSameMapper.INSTANCE.dtoToEntity(dto = goodallDto)

        entity.name shouldBe goodallDto.name
        entity.age shouldBe goodallDto.age
    }

    @DisplayName("필드 명이 다르다면 필드 매핑을 해주어야한다.")
    @Test
    fun mapStructTest2() {
        val goodallDto = GoodallDto(name = "yoonsung", age = 32)
        val entity = GoodallDiffMapper.INSTANCE.dtoToEntity(dto = goodallDto)

        entity.nickName shouldBe goodallDto.name
        entity.age shouldBe goodallDto.age
    }

    @DisplayName("VO로 받는경우, 매핑코드가 있어야한다.")
    @Test
    fun mapStructVoTest1() {
        val unluckyJungDto = UnluckyJungDto(name = "yoonsung", age = 32)
        val entity = UnlukcyJungV2Mapper.INSTANCE.dtoToEntity(dto = unluckyJungDto)
        val dto = UnlukcyJungV2Mapper.INSTANCE.entityToDto(entity = entity)

        entity.name.value shouldBe unluckyJungDto.name
        entity.age shouldBe unluckyJungDto.age

        entity.age shouldBe dto.age
        entity.name.value shouldBe dto.name

        dto.age shouldBe entity.age
        dto.name shouldBe entity.name.value
    }

    @DisplayName("String 으로 받고 VO 로 매핑하면 JvmStatic 작업이 불필요하다.")
    @Test
    fun mapStructVoTest2() {
        val unluckyJungDto = UnluckyJungDto(name = "yoonsung", age = 32)
        val entity = UnlukcyJungV1Mapper.INSTANCE.dtoToEntity(dto = unluckyJungDto)
        val dto = UnlukcyJungV1Mapper.INSTANCE.entityToDto(entity = entity)

        entity.name.value shouldBe unluckyJungDto.name
        entity.age shouldBe unluckyJungDto.age

        entity.age shouldBe dto.age
        entity.name.value shouldBe dto.name

        dto.age shouldBe entity.age
        dto.name shouldBe entity.name.value
    }

    @DisplayName("source 쪽에 필드가없는상태에서 매핑 디폴트값을 안정해주면, target 에 디폴트값이 있어도, 매핑과정에서 강제로 null 로 채우려고 해서 에러가 난다.")
    @Test
    fun mapStructConstTest() {
        val yoonsungDto = YoonsungDto(name = "yoonsung", age = 32)

        shouldThrowExactly<NullPointerException> {
            YoonsungV1Mapper.INSTANCE.dtoToEntity(dto = yoonsungDto)
        }
    }

    @DisplayName("source 쪽에 필드값이 없으면, constant 를 이용해 ㄴtarget 쪽에 필드값을 채워줄 수 있다.")
    @Test
    fun mapStructConstTest2() {
        val yoonsungDto = YoonsungDto(name = "yoonsung", age = 32)
        val entity = YoonsungV3Mapper.INSTANCE.dtoToEntity(dto = yoonsungDto)

        entity.age shouldBe yoonsungDto.age
        entity.name shouldBe yoonsungDto.name
        entity.nickName shouldBe "goodall"
    }

    @DisplayName("디폴트값이 있어도, constant 값으로 매핑이된다.")
    @Test
    fun mapStructConstTest3() {
        val yoonsungDto = YoonsungDto(name = "yoonsung", age = 32)
        val entity = YoonsungV2Mapper.INSTANCE.dtoToEntity(dto = yoonsungDto)

        entity.age shouldBe yoonsungDto.age
        entity.name shouldNotBe yoonsungDto.name
        entity.name shouldBe "goodall"
    }
}

