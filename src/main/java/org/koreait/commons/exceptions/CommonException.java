package org.koreait.commons.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter @Getter
public class CommonException extends RuntimeException {
    private HttpStatus status;

    public CommonException(String message) {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR); // 500
    }

    public CommonException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}

//    public HttpStatus getStatus() { 세터와 게터를 추가했기때문에 필요없음
//        return status;
//    }
//}
