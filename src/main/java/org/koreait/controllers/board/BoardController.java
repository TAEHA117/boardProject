package org.koreait.controllers.board;

import lombok.RequiredArgsConstructor;
import org.koreait.commons.MemberUtil;
import org.koreait.commons.ScriptExceptionProcess;
import org.koreait.commons.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


// 시작
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor // 의존성 추가할 수 있게 어노테이션 적용
public class BoardController implements ScriptExceptionProcess {
    private final Utils utils; // 회원 접근권한 처리 -> 회원정보를 가져와야함
    private final MemberUtil memberUtil;

    @GetMapping("/write/{bId}") // 게시판 id를 가져옴
    // commonprosess 가 추가될 수 있기 때문에 Model 적용
    public String write(@PathVariable String bId, Model model) { // 필수로 필요한 항목 String bId
        return utils.tpl("board/write"); // 모바일도 알아서 바뀔수 있게 utils.tpl 적용
    }

    @GetMapping("/update/{seq}")
    public String update(@PathVariable Long seq, Model model) { // 게시글 번호 가져옴 - Long seq
        return utils.tpl("board/update"); // 마찬가지로 모바일 등 알아서 바뀔 수 있게 적용

    }

    // 저장처리할 부분 - 게시글 쓰기,수정 동일하게 넣어줄 것
    @PostMapping("/save") // 게시글 저장이 완료되면 -> 상세보기로 넘어가게
    public String save(Model model) {
        return "redirect:/board/list/게시판ID"; // 게시판 id로 이동
    }

    @GetMapping("/view/{seq}")
    public String view(@PathVariable Long seq, Model model) { // 게시글보기는 게시글 번호로

        return utils.tpl("board/view"); // 모바일도 알아서 바뀔수 있게 utils.tpl 적용
    }


    // 게시글 삭제
    @GetMapping("/delete/{seq}") // 게시글 삭제도 게시글 번호로
    public String delete(@PathVariable Long seq) {

        return "redirect:/board/list/게시판 ID"; // 게시판 id로 이동
    }


    private void commonProcess(String bId, String mode, Model model) {
        // 모드에 따라서 제목 완성
        // bId로 설정조회
        // 쓰기에 필요한 edit가져와서처리
        String pageTitle = "게시글 목록";
        if (mode.equals("write")) pageTitle = "게시글 작성";
        else if (mode.equals("update")) pageTitle = "게시글 수정";
//        else if (mode.equals("delete")) pageTitle = "게시글 삭제"; 삭제는 일단 필요없음
        else if (mode.equals("view")) pageTitle = "게시글 제목";

        model.addAttribute("pageTitle",pageTitle);
    }
}
