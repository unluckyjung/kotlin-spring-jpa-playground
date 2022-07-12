package com.example.kopring.basic.config

import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CursorInterceptor : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {

        // getParameter 리턴값이 String?이 아니라 String이라서 주의 필요
        val cursorUuid = request.getParameter("cursorUuid")
        val cursorValue = request.getParameter("cursorValue")

        if (cursorValue.isNullOrEmpty() && cursorUuid.isNullOrEmpty()) return true
        cursorValidation(cursorUuid, cursorValue)
        return true
    }

    private fun cursorValidation(cursorUuID: String?, cursorValue: String?) {

        if (cursorUuID.isNullOrEmpty() && cursorValue.isNullOrEmpty().not()) {
            throw Exception("cursor Error")
        }
    }
}
