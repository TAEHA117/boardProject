package org.koreait.controllers.admins;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class BoardConfigForm {
    @NotBlank(message="게시판 아이디를 입력하세요") // 필수항목
    private String bid; // 게시판 아이디 - 필수항목

    @NotBlank(message="게시판 이름을 입력하세요") // 필수항목
    private String bName; // 게시판 이름 - 필수항목

    private boolean active;

    private String authority = "ALL";

    private String category;
}
