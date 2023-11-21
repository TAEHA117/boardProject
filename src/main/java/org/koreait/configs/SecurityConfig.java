package org.koreait.configs;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.koreait.models.member.LoginFailureHandler;
import org.koreait.models.member.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

@Configuration
@EnableConfigurationProperties(FileUploadConfig.class)
public class SecurityConfig {

    @Autowired
    private FileUploadConfig fileUploadConfig;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /* 인증설정 - 로그인 S */
        http.formLogin(f -> {
            f.loginPage("/member/login")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .successHandler(new LoginSuccessHandler())
                    .failureHandler(new LoginFailureHandler());
//                    .successForwardUrl("/") // 로그인 성공 시 여기로 이동 하는 메서드
                    //                    .failureUrl("/member/login?error=true"); // 실패했을 시 여기로 이동하는 메서드

        }); // DSL

        http.logout(c -> {
            c.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                    .logoutSuccessUrl("/member/login");
        });

        /* 인증 설정 - 로그인 E */

        http.headers(c -> {
           c.frameOptions(o -> o.sameOrigin()); // 막혀있는 iframe -> 풀어주는거
        });

        /* 인가 설정 - 접근 통제 S */
        http.authorizeHttpRequests(c -> {
           c.requestMatchers("/mypage/**").authenticated() // 회원 전용 (로그인한 회원만 접근 가능)
// 배포할 때는 주석을 풀고 사용                  .requestMatchers("/admin/**").hasAuthority("ADMIN") // 관리자 권한만 접근
                   .requestMatchers(
                           "/front/css/**",
                           "/front/js/**",
                           "/front/images/**",

                           "/mobile/css/**",
                           "/mobile/js/**",
                           "/mobile/images/**",

                           "/admin/css/**",
                           "/admin/js/**",
                           "/admin/images/**",

                           "/common/css/**",
                           "/common/js/**",
                           "/common/images/**",
                           fileUploadConfig.getUrl() + "**"
                   ).permitAll()
                   .anyRequest().permitAll(); // 나머지 페이지는 권한 필요없이 자유롭게 접근할 수 있게
        });

        http.exceptionHandling(c -> { // <- 인증 실패했을 때
           c.authenticationEntryPoint((req, resp ,e) ->  {
               String URI = req.getRequestURI();
               if (URI.indexOf("/admin") != -1 ) { //관리자 페이지 - 401 응답 코드를 내보낼 것이다.
                    resp.sendError(HttpServletResponse.SC_UNAUTHORIZED ," NOT AUTHORIZED"); // HttpServletResponse.SC_UNAUTHORIZED -> 정수형 401 대신 상수를 활용했다.
               } else { // 회원전용 페이지 (예 - /mypage) -> 로그인 페이지로 이동
                    String url = req.getContextPath() + "/member/login";
                    resp.sendRedirect(url);
               }
           });
        });

        /* 인가 설정 - 접근 통제 E */

        return http.build();
    }

    /*@Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 시큐리티 설정이 적용될 필요가 없는 경로 설정
        return w -> w.ignoring().requestMatchers(
                "/front/css/**",
                "/front/js/**",
                "/front/images/**",

                "/mobile/css/**",
                "/mobile/js/**",
                "/mobile/images/**",

                "/admin/css/**",
                "/admin/js/**",
                "/admin/images/**",

                "/common/css/**",
                "/common/js/**",
                "/common/images/**",
                fileUploadConfig.getUrl() + "**");
        }
        -> 현 버전 -> 오류는 아니지만 권장사항이 아니다
        -> 이 커스터마이저에 있는 로직을 -> 인가 설정에 있는 -> .requestMatchers()를 생성하고 넣어준다.
        */


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
