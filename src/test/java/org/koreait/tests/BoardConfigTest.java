package org.koreait.tests;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.koreait.entities.Board;
import org.koreait.repositories.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.util.ExceptionCollector;

import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.*;
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

    @Autowired
    private BoardRepository boardRepository;

    @Test
    @DisplayName("게시판 설정 저장 테스트 - 유효성 검사 테스트")
    void boardConfigTest() throws Exception {
        String body = mockMvc.perform(
                    post("/admin/board/save")
                        .with(csrf()) // csrf -> 토큰 추가
                )
                .andDo(print()) // 요청과 응답의 상세정보를 콘솔에서 확인하는 방법이다 -> 서버를 켜지 않았을 때 사용하는듯?
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        assertTrue(body.contains("게시판 아이디"));
        assertTrue(body.contains("게시판 이름"));
    }

    @Test
    @DisplayName("게시판 설정 저장 테스트 - 성공시 302")
    void boardConfigTest2() throws Exception {
        mockMvc.perform(post("/admin/board/save")
                .param("bId","notice")
                .param("bName","공지사항")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/admin/board"));

        // 실제 DB에도 설정 값이 있는지 체크
        Board board = boardRepository.findById("notice").orElse(null);
        assertNotNull(board);

        assertTrue(board.getBName().contains("공지사항"));
    }
}
