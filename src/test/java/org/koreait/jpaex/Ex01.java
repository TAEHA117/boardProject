package org.koreait.jpaex;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.koreait.commons.constants.MemberType;
import org.koreait.entities.Member;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
//@TestPropertySource(activeProfiles="test")
public class Ex01 {

    @PersistenceContext // jpa에 반영된 영속성 의존성 주입
    private EntityManager em;

    @BeforeEach
    void init() {
        Member member = new Member();
//        member.setUserNo(1L);
        member.setEmail("user01@test.org");
        member.setUserNm("사용자01");
        member.setPassword("123456");
        member.setMobile("01010000000");
        member.setMtype(MemberType.USER);


        em.persist(member); // insert // 변화 감지 상태
        em.flush();
        em.clear(); // 영속성상태 비우기 -> 변화감지를 하지 않는다.
    }

    @Test
    void test2() {
        Member member = em.find(Member.class, 1L); // DB -> 영속성 컨텍스트로 추가 // find-> 기본키로 조회
        System.out.println(member);

        Member member2 = em.find(Member.class, 1L); // 영속성 컨텍스트 -> 조회
        System.out.println(member2);

        TypedQuery<Member> query = em.createQuery("SELECT m FROM Users AS m WHERE m.email LIKE :key", Member.class);
        query.setParameter("key","%user%");
        Member member3 = query.getSingleResult();

        member3.setUserNm("(수정)사용자01");
        em.flush();
    }


    @Test
    void test1() {
        Member member = new Member();
        member.setUserNo(1L);
        member.setEmail("user01@test.org");
        member.setUserNm("사용자01");
        member.setPassword("123456");
        member.setMobile("01010000000");
        member.setMtype(MemberType.USER);


        em.persist(member); // insert // 변화 감지 상태
        em.flush();

        em.detach(member); // 영속성 분리, 변화 감지 X -> detach

        member.setUserNm("(수정)사용자01"); // 업데이트 update
        em.flush();

        em.merge(member); // 분리된 영속 상태 -> 영속 상태, 변화감지 O
        em.flush();

//        em.remove(member); // 제거상태(영속성안에 있는 중) -> flush를 해줬을 때
//        em.flush();
//        remove를 하면 영속성안에 있는거고 제거상태인데 -> flush를 했을 때 제거상태를 반영하는건지 여쭙기
    }

    @Test
    void test3() {

    }
}
