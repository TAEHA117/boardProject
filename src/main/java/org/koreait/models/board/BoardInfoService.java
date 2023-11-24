package org.koreait.models.board;

import lombok.RequiredArgsConstructor;
import org.koreait.entities.BoardData;
import org.koreait.models.board.config.BoardNotFoundException;
import org.koreait.repositories.BoardDataRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // 의존성추가
public class BoardInfoService {
    private final BoardDataRepository boardDataRepository;

    public BoardData get(Long seq) { // 게시글 번호로 조회할 수 있게

        BoardData data = boardDataRepository.findById(seq).orElseThrow(BoardNotFoundException::new);

        return data;

    }
}
