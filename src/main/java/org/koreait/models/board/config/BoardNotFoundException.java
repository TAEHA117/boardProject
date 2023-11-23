package org.koreait.models.board.config;

import org.koreait.commons.Utils;
import org.koreait.commons.exceptions.AlertBackException;
import org.springframework.http.HttpStatus;

public class BoardNotFoundException extends AlertBackException {
    public BoardNotFoundException() {
        // 게시판 삭제에 제공되는 메시지는 하나면 충분
        // 고정으로 잡아준다 404
        super(Utils.getMessage("NotFound.board","error"));
        setStatus(HttpStatus.NOT_FOUND);
    }
}
