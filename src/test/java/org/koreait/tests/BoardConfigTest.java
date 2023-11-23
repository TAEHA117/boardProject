package org.koreait.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties="spring.profiles.active=test")
public class BoardConfigTest{ // 서버가 닫혀있어도 테스트이 가능하다. 예시 test클래스
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("게시판 설정 저장 테스트")
    void boardConfigTest() throws Exception {
        mockMvc.perform(
                post("/admin/board/save")
                        .with(csrf())
                )
                .andDo(print()); // 요청과 응답의 상세정보를 콘솔에서 확인하는 방법이다 -> 서버를 켜지 않았을 때 사용하는듯?
    }


}
