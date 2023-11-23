package org.koreait.models.board.config;


import lombok.RequiredArgsConstructor;
import org.koreait.commons.constants.BoardAuthority;
import org.koreait.controllers.admins.BoardConfigForm;
import org.koreait.entities.Board;
import org.koreait.repositories.BoardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class BoardConfigSaveService { // boardsaveservice
    private final BoardRepository boardRepository;

    public void save(BoardConfigForm form) { // 커맨드객체인 BoardConfigForm이 들어옴

        String bId = form.getBId();
        String mode = form.getMode();
        Board board = null;
        if (mode.equals("edit") && StringUtils.hasText(bId)) {
            board = boardRepository.findById(bId).orElseThrow(BoardNotFoundException::new);
        } else {
            board = new Board(); // 기본키인 bId는 최초로 한번만 추가가 된다.
            board.setBId(bId);
        }

        board.setBName(form.getBName());
        board.setActive(form.isActive());
        board.setAuthority(BoardAuthority.valueOf(form.getAuthority())); // enum상수로 바꿔주는 것
        board.setCategory(form.getCategory());

        boardRepository.saveAndFlush(board);


    }
}
