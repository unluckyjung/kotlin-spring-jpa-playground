package com.example.kopring.member.domain

import com.example.kopring.test.IntegrationTest
import io.kotest.assertions.throwables.shouldThrowExactly
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientRequestException
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono

@IntegrationTest
class WebClientTest {

    val webClient = WebClient.builder().build()

    @Test
    fun webClientGet() {
        val googleUrl = "https://www.google.com/"

        val response = webclientExecute(googleUrl)
        response.block()
    }

    @Test
    fun webClientGetFail() {
        val noExistUrl = "https://www.ddoggle.com/"

        val response = webclientExecute(noExistUrl)

        // 아예 없는 url에 접근하면 예외발생
        shouldThrowExactly<WebClientRequestException> {
            response.block()
        }
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
