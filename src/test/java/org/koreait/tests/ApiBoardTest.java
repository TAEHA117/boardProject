package org.koreait.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.koreait.controllers.board.BoardForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "spring.profiles.active=test")
public class ApiBoardTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("글 작성 성공시 201 응답")
    void boardSaveTest() throws Exception { // 통합테스트 - 컨트롤러에 기능 합쳐서 통합으로 테스팅한것

        BoardForm form = new BoardForm();
        form.setBId("notice");
        form.setSubject("제목");
        form.setContent("내용");
        form.setPoster("작성자");

        ObjectMapper om = new ObjectMapper();
        String params = om.writeValueAsString(form); // json 변환 형태로

        mockMvc.perform(post("/api/v1/board/write/notice")
                .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("UTF-8")
                        .content(params)
        ).andDo(print()).andExpect(status().isCreated()); // andDo(print()) -> 많이 쓰는거니까 숙지해보자
    }

}
