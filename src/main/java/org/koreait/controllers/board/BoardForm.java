package org.koreait.controllers.board;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BoardForm { // 커맨드객체 -> 검증 적용'

    private String mode;
    private Long seq; // 수정할 때필요한 데이터 / 게시글을 조회할 떄 필요하기도 함
    private String bId; // 작성할 때만 필요한

    @NotBlank(message = "제목을 입력하세요")
    private String subject;

    @NotBlank(message = "작성자를 입력하세요")
    private String poster; // 작성자

    @NotBlank(message = "내용을 입력하세요")
    private String content;



}
