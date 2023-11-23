package org.koreait.models.board.config;


import lombok.RequiredArgsConstructor;
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
        if (StringUtils.containsWhitespace("edit") && StringUtils.hasText(bId)) {
            board = boardRepository.findById(bId).orElseThrow(BoardNotFoundException::new);
        }


    }
}
