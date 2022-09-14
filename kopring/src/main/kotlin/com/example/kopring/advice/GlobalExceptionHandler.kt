package com.example.kopring.advice

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(value = [Exception::class])
    fun basic(e: Exception): ResponseEntity<ExceptionDto> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ExceptionDto(e.toString(), value = null)
        )
    }

    @ExceptionHandler(value = [HttpMessageNotReadableException::class])
    @ResponseBody
    fun dtoTypeMissMatchException(exception: HttpMessageNotReadableException): ResponseEntity<ExceptionDto> {
        val msg = when (exception.cause) {
            is InvalidFormatException -> {
                val causeException = exception.cause as InvalidFormatException
                "입력 받은 ${causeException.value} 를 ${causeException.targetType} 으로 변환중 에러가 발생했습니다."
            }

            is MissingKotlinParameterException -> {
                val causeException = (exception.cause as MissingKotlinParameterException)
                "Parameter is missing: ${causeException.parameter.name}"
            }

            else -> "요청을 역직렬화 하는과정에서 예외가 발생했습니다."
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ExceptionDto(message = msg, value = null)
        )
    }
}

data class ExceptionDto(
    val message: String = "default Msg",
    val field: String = "default field",
    val value: Any?,
)
