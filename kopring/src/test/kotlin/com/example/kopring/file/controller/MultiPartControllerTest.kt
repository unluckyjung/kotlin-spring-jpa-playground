package com.example.kopring.file.controller

import com.example.kopring.test.IntegrationTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.mock.web.MockMultipartFile
import org.springframework.mock.web.MockPart
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.multipart

@AutoConfigureMockMvc
@IntegrationTest
class MultiPartControllerTest(
    private val mockMvc: MockMvc,
) {
    @DisplayName("파일 업로드 테스트")
    @Test
    fun uploadTest() {
        val csvFile = MockMultipartFile(
            "file",
            "mockFile.csv",
            // String 형태로 넣어줘야 한다.
            "multipart/form-data",
            "1,2,3".toByteArray()
        )

        val jsonFile = MockMultipartFile("dto", "", "application/json", "{\"name\": \"jys\", \"age\": \"30\"}".toByteArray())

        val testFilename = "testCodeFilename"
        val testId = 1L

        mockMvc.multipart("/api/v1/file/$testId") {
            file(csvFile)
                // @RequestParam filename: String 처리를 .part 로도 가능하다.
//                .part(MockPart("filename", testFilename.toByteArray()))
                .param("filename", testFilename)
            file(jsonFile)
        }.andExpect {
            status { isOk() }
        }.andDo { }
    }

    @DisplayName("파일 업로드 테스트(ModelAttribute)")
    @Test
    fun uploadTest2() {
        val csvFile = MockMultipartFile(
            "file",
            "mockFile.csv",
            "multipart/form-data",
            "1,2,3".toByteArray()
        )

        val testFilename = "testCodeFile"
        val testId = 1L

        mockMvc.multipart("/api/v1/file/$testId/attribute") {
            file(csvFile)
                .part(MockPart("name", "jys".toByteArray()))
                .part(MockPart("age", "30".toByteArray()))
                .param("filename", testFilename)
        }.andExpect {
            status { isOk() }
        }.andDo { }
    }
}
