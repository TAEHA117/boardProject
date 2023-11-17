package org.koreait.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.koreait.commons.constants.MemberType;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@Entity
@NoArgsConstructor @AllArgsConstructor
@Table(indexes = {
        @Index(name="idx_member_userNm",columnList = "userNm"),
        @Index(name="idx_member_mobile",columnList = "mobile")
}) // 테이블에 대해 상세하게 설정가능한 어노테이션
public class Member extends Base{

    @Id @GeneratedValue(strategy = GenerationType.AUTO) // 기본적으로 시퀀스 객체가 하나 만들어진다.
    private Long userNo;

    // // varchar2의 길이 조절 및 null값을 허용하지 않게 설정 / unique값을 줌
    @Column(unique = true, nullable = false, length = 65)
    private String email;

    // varchar2의 길이 조절 및 null값을 허용하지 않게 설정 / name을 pw로 설정
    @Column(name="pw", nullable = false, length = 65)
    private String password;

    @Column(nullable = false, length = 40) // varchar2의 길이 조절 및 null값을 허용하지 않게 설정
    private String userNm;

    @Column(length = 11) // varchar2의 길이 조절 및 null값을 허용하지 않게 설정
    private String mobile;

    @Column(length=10, nullable = false) // varchar2의 길이 조절 및 null값을 허용하지 않게 설정
    @Enumerated(EnumType.STRING) // type을 String으로 줘야함 -> ADMIN / USER 상수의 경로가 되는 것 -> MemberType 클래스와 관련
    private MemberType mtype = MemberType.USER;


}
