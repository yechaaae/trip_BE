package com.ssafy.enjoytrip.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        
        // 1. 요청 메서드가 OPTIONS(Preflight)인 경우 통과 (CORS 처리를 위해 필수)
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        // 2. 세션 검사
        HttpSession session = request.getSession();
        if (session.getAttribute("userInfo") == null) {
            // 로그인 안 된 상태면 401 에러 응답
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false; // 컨트롤러로 진입하지 못하게 차단
        }

        // 3. 로그인 된 상태면 통과
        return true;
    }
}