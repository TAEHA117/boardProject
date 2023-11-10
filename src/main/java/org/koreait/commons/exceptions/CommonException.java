package org.koreait.commons.exceptions;

import org.springframework.http.HttpStatus;

public class CommonException extends RuntimeException {

    private HttpStatus status;

    public CommonException(String message) {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR); // 기본적으로 500에러
    }

    public CommonException(String message, HttpStatus status) { // 내가 설정한 에러정의
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
