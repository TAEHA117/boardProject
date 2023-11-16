package org.koreait.commons.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.koreait.commons.Utils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class CommonInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 어떠한 인증인가를 하지않는다. -> 공통적인 부분 처리
        init(request); // 초기화 - 장비에 대한 부분
        return true;
    }

    private void init(HttpServletRequest request) {
        HttpSession session = request.getSession();


        /* PC, Mobile 수동 변경 처리 S */
        String device = request.getParameter("device");
        if (device != null && !device.isBlank()) {
            session.setAttribute("device",device.toLowerCase().equals("mobile")?"mobile":"pc");
        }
        /* PC, Mobile 수동 변경 처리 E */

        /* 로그인 페이지 아닐 경우 로그인 유효성 검사 세션 삭제 처리*/
        String URI = request.getRequestURI();
        if (URI.indexOf("/member/login") == -1) {
            Utils.loginInit(session);
        }
    }
}
