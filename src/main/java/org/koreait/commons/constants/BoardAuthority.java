package org.koreait.commons.constants;

import java.util.Arrays;
import java.util.List;

public enum BoardAuthority {
    ALL("비회원+회원+관리자"),
    MEMBER("회원+관리자"),
    ADMIN("관리자");

    // 초기화를 해줘야 사용할 수 있음. 밑 this.로 초기화함
    private final String title;

    BoardAuthority(String title) {
        // 한글로 출력할 수 있게 설정
        this.title = title;
    }

    // 0~1 ADMIN , MEMBER 추가 -> 나머지 String title
    public static List<String[]> getList() {
        return Arrays.asList(
          new String[] { ALL.name(), ALL.title },
          new String[] { MEMBER.name(), MEMBER.title},
          new String[] { ADMIN.name(), ADMIN.title}
        );
    }

    public String getTitle() {
        return title;
    }
}
