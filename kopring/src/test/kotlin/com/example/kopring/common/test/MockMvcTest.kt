package com.example.kopring.common.test

import com.example.kopring.test.IntegrationTest
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.bind.annotation.*

@IntegrationTest // JPA Auditing 때문에 @WebMvcTest 대신 @SpringBootTest 사용
@AutoConfigureMockMvc
class MockMvcTest(
    val mockMvc: MockMvc
) {

//    @Autowired
//    lateinit var mockMvc: MockMvc

    @Test
    fun helloTest() {
        mockMvc.perform(
//            MockMvcRequestBuilders.get("/test/v1/hello")  // MockMvcRequestBuilders를 써야함 주의
            get("/test/v1/hello")
        ).andExpect(
            status().isOk
        ).andExpect(MockMvcResultMatchers.content().string("hello"))
    }

    @Test
    fun queryParamsTest() {

        // 반드시 LinkedMultiValueMap 이여야 함
        val queryParams = LinkedMultiValueMap<String, String>()
        queryParams["age"] = "30"
        queryParams["name"] = "jys"

        mockMvc.perform(
            get("/test/v1/queryParams")
//                .params(queryParams)  // 가능
                .queryParams(queryParams)
        ).andExpect(
            // 하나씩 검증가능
            status().isOk
        ).andExpectAll(
            // 한꺼번에 검증가능
            jsonPath("\$.name").value("jys"),
            jsonPath("\$.age").value("30")
        )
    }

    @Test
    fun jsonPostTest() {

        val request = Request(28, "goodall")

        // object to json
        val jsonBody = jacksonObjectMapper().writeValueAsString(request)

        mockMvc.perform(
            post("/test/v1/json-post").content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)

        ).andExpectAll(
            jsonPath("\$.name").value("goodall"),
            jsonPath("\$.age").value("28")
        ).andDo(
            // 결과 출력 예시
            MockMvcResultHandlers.print()
        )
    }
}

@RequestMapping("test/v1/")
@RestController
class TestController {

    @GetMapping("hello")
    fun hello(): String {
        return "hello"
    }

    @GetMapping("queryParams")
    fun queryParams(@ModelAttribute req: Request): Response {
        return Response(
            req.age,
            req.name
        )
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("json-post")
    fun jsonPost(@RequestBody req: Request): Response {
        return Response(
            req.age,
            req.name
        )
    }
}

data class Request(
    val age: Int,
    val name: String,
)

data class Response(
    val age: Int,
    val name: String
)