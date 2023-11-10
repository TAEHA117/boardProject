package org.koreait.commons.rest;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class JSONData<T> { // 다양한 자료형으로 지네릭스 타입을 쓴다
    private boolean success = true;
    private HttpStatus status = HttpStatus.OK;

    @NonNull
    private T data;
    private String message; // success가 false일 때 알려줄 메시지 설정
}
