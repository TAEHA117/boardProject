package org.koreait.jpeex;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.koreait.commons.constants.MemberType;
import org.koreait.entities.BoardData;
import org.koreait.entities.Member;
import org.koreait.models.board.BoardListService;
import org.koreait.repositories.BoardDataRepository;
import org.koreait.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
@TestPropertySource(properties = "spring.profiles.active=test")
public class Ex02 {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BoardDataRepository boardDataRepository;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private BoardListService listService;


    @BeforeEach
    void init() {
        Member member = Member.builder()
                .email("user01@test.org")
                .password("12345678")
                .userNm("사용자01")
                .mtype(MemberType.USER)
                .mobile("01000000000")
                .build();
        memberRepository.saveAndFlush(member);

        List<BoardData> items = new ArrayList<>();
        for (int i = 1; i<= 10; i++) {
            BoardData item = BoardData.builder()
                    .subject("제목" + i)
                    .content("내용" + i)
                    .member(member)
                    .build();
            items.add(item);
        }
        boardDataRepository.saveAllAndFlush(items);
        em.clear();
    }

    @Test
    void test1() {
        List<BoardData> items = boardDataRepository.findAll(); // 전체를 가져온다 - 게시글 10개가 나올 것이다. // 1차쿼리
        for (BoardData item:items) {
            Member member = item.getMember();
            String email = member.getEmail(); // 2차쿼리
            System.out.println(email);
        }
    }

    @Test
    void test2() {
        List<BoardData> items = boardDataRepository.getList2();

    }

    @Test
    void test3() {
        List<BoardData> items = listService.getList();
    }

    @Test
    void test4() {
        List<BoardData> items = boardDataRepository.findBySubjectContaining("목");
    }

    @Test
    void test5() {
        Member member = memberRepository.findByEmail("user01@test.org").orElse(null);
        List<BoardData> items = member.getItems(); // 실제 데이터를 사용할 때 가져오게될것이다.
        items.stream().forEach(System.out::println);
        //items에서 출력하였을 때 사용하게 되는것

        memberRepository.delete(member);
        memberRepository.flush(); // 참조무결설제약조건위배 -> member에서 외래키가 걸려있기때문에 자식 삭제 불가

    }

}
