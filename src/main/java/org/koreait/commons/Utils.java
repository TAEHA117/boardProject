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

    private final HttpServletRequest request; // @RequiredArgsConstructor -> 적용

    private final HttpSession session;

    static {
        validationsBundle = ResourceBundle.getBundle("messages.validations");
        errorsBundle = ResourceBundle.getBundle("messages.errors");
    }

    public static String getMessage(String code, String bundleType) {
        bundleType = Objects.requireNonNull(bundleType, "validation");
        ResourceBundle bundle = bundleType.equals("error")? errorsBundle : validationsBundle;

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
}
