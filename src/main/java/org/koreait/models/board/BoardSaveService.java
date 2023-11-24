package org.koreait.models.board;


import lombok.RequiredArgsConstructor;
import org.koreait.controllers.board.BoardForm;
import org.koreait.entities.Board;
import org.koreait.entities.BoardData;
import org.koreait.repositories.BoardDataRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor // 의존성 추가
public class BoardSaveService { // 게시글 저장 서비스
    private final BoardDataRepository boardDataRepository; // 자동 의존성 주입

    public void save(BoardForm form) {
        Long seq = form.getSeq();
        String mode = Objects.requireNonNullElse(form.getMode(),"add");

        BoardData data = null;
        if (mode.equals("update") && seq != null) {
            data = boardDataRepository.findById(seq).orElseThrow(BoardDataNotFoundException::new);
        } else {
            data = new BoardData();
        }

        data.setSubject(form.getSubject());
        data.setContent(form.getContent());
        data.setPoster(form.getPoster());

        boardDataRepository.saveAndFlush(data);

    }
}
