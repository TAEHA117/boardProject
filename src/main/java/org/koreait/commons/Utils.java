package org.koreait.commons;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class Utils {
    private static ResourceBundle validationsBundle;
    private static ResourceBundle errorsBundle;

    private static ResourceBundle commonsBundle; // <- 팀프로젝트 소스파일에 없음 / 추 후 추가 검토

    private final HttpServletRequest request; // @RequiredArgsConstructor -> 적용

    private final HttpSession session;

    static {
        validationsBundle = ResourceBundle.getBundle("messages.validations");
        errorsBundle = ResourceBundle.getBundle("messages.errors");
        commonsBundle = ResourceBundle.getBundle("messages.commons"); //   <- 팀프로젝트 소스파일에 없음 / 추 후 추가 검토
    }

    public static String getMessage(String code, String bundleType) {
        bundleType = Objects.requireNonNull(bundleType, "validation");

        ResourceBundle bundle = null;

        if (bundleType.equals("common")) {
            bundle = commonsBundle;
        } else if (bundleType.equals("error")) {
            bundle = errorsBundle;
        } else {
            bundle = validationsBundle;
        }


        try {
            return bundle.getString(code);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isMobile() {
        String device = (String)session.getAttribute("device");
        if (device != null) {
            return device.equals("mobile");
        }

        // 현재 접속한 장비가 모바일인지 컴퓨터인지 check
        // 요청 헤더 User_Agent -> 모바일인지 -> PC인지 구분 -> Request객체가 필요하다.
        boolean isMobile = request.getHeader("User-Agent").matches(".*" +
                "(iPhone|iPod|iPad|BlackBerry|Android|Windows CE|LG|MOT|SAMSUNG|SonyEricsson).*");

        return isMobile;
    }

    public String tpl(String tplPath) {
        return String.format("%s/" + tplPath, isMobile()?"mobile":"front"); // 앞에 s가 프론트가 되거나 모바일이 될것이다.

    }

    public static void loginInit(HttpSession session) {
        session.removeAttribute("email");
        session.removeAttribute("NotBlank_email");
        session.removeAttribute("NotBlank_password");
        session.removeAttribute("globalError");
    }

    /**
     * 단일 요청 데이터 조회
     */
    public String getParam(String name) {
        return request.getParameter(name);
    }

    /**
     * 복수개 요청 데이터 조회
     *
     */
    public String[] getParams(String name) {
        return request.getParameterValues(name);
    }


    public static int getNumber(int num, int defaultValue) {
        return num <= 0 ? defaultValue : num;
    }

    /**
     * 비회원 구분 UID
     * 비회원 구분은 IP + 브라우저 종류
     *
     */
    public int guestUid() { // 예) 비회원이 장바구니 이용 -> 기록 로그 O -> 로그인했을 때 유저의 장바구니로 취합
        String ip = request.getRemoteAddr();
        String ua = request.getHeader("User-Agent");

        return Objects.hash(ip, ua);
    }
}

