package org.koreait.controllers.admins;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.koreait.commons.ScriptExceptionProcess;
import org.koreait.commons.menus.Menu;
import org.koreait.models.board.config.BoardConfigSaveService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller("adminBoardController")
@RequestMapping("/admin/board")
@RequiredArgsConstructor
public class BoardController implements ScriptExceptionProcess {

    private final HttpServletRequest request;
    private final BoardConfigSaveService saveService;

    @GetMapping
    public String list(Model model) {

        commonProcess("list",model);

        return "admin/board/list";
    }

    @GetMapping("/add")
    public String register(@ModelAttribute BoardConfigForm form, Model model) {

        commonProcess("add", model);

        return "admin/board/add";
    }

    @GetMapping("/edit/{bId}")
    public String update(@PathVariable String bId, Model model) {

        commonProcess("edit",model);

        return "admin/board/edit";
    }

    //게시판 설정을 저장하는 save
    @PostMapping("/save")
    public String save(@Valid BoardConfigForm form, Errors errors, Model model) {

        String mode = Objects.requireNonNullElse(form.getMode(),"add"); // null값에 대한 오류 해결 -> Objects.requiredNonnull 추가
        commonProcess(mode, model);

        if (errors.hasErrors()) {
            return "admin/board/" + mode;
        }

        saveService.save(form);
        return "redirect:/admin/board";
    }

    private void commonProcess(String mode, Model model) { // 상단 제목처리  / 공통적으로 처리해야할 부분 예) submit / 메뉴체크 등
        String pageTitle = "게시판 목록";
        mode = Objects.requireNonNullElse(mode, "list");
        if (mode.equals("add")) pageTitle = "게시판 등록";
        else if (mode.equals("edit")) pageTitle = "게시판 수정";

        model.addAttribute("pageTitle",pageTitle);
        model.addAttribute("menuCode","board");
        model.addAttribute("submenus", Menu.gets("board"));
        model.addAttribute("subMenuCode",Menu.getSubMenuCode(request));
    }
}
