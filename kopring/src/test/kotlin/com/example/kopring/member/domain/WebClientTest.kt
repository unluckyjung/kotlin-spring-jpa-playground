package com.example.kopring.member.domain

import com.example.kopring.test.IntegrationTest
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.string.shouldContain
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientRequestException
import org.springframework.web.reactive.function.client.bodyToMono
import org.springframework.web.reactive.function.client.toEntity
import reactor.core.publisher.Mono

@IntegrationTest
class WebClientTest {

    private val webClient = WebClient.builder().build()
    private val googleUrl = "https://www.google.com/"
    private val noExistUrl = "https://www.ddoggle.com/"
    private val notFoundUrl = "https://github.com/unluckyjung1/"

    @Test
    fun webClientGet() {
        val response = webclientExecute(googleUrl)
        response.block()
    }

    @Test
    fun webClientGetFail() {

        val response = webclientExecute(noExistUrl)

        // 아예 없는 url에 접근하면 WebClientRequestException 예외발생
        shouldThrowExactly<WebClientRequestException> {
            response.block()
        }
    }

    @Test
    fun webClientStatusCodeCheck() {
        val response = webClient.get()
            .uri("https://www.google.com/")
            .retrieve()
            .bodyToMono<String>()

        val responseString = response.block()
    }

    @Test
    fun webClient400StatusCodeCheck() {
        val response = webClient.get()
            .uri(notFoundUrl)
            .retrieve()
            .onStatus(HttpStatus::is4xxClientError) {
                throw IllegalArgumentException("400번대 에러 발생")
            }
            .onStatus(HttpStatus::is5xxServerError) {
                throw IllegalArgumentException("500번대 에러 발생")
            }.toEntity<String>()

        shouldThrowExactly<IllegalArgumentException> {
            response.block()
        }.message shouldContain "400번대 에러 발생"
    }


    private fun webclientExecute(url: String): Mono<String> {
        val response = webClient.get().uri(url).exchangeToMono {
            if (it.statusCode().isError) {
                LOG.info("error!")
                throw Exception("400대 에러발생!")
            } else {
                LOG.info("ok!")
                it.bodyToMono<String>()
            }
        }
        return response
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(this::class.java)
    }
}
