package org.koreait.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.koreait.commons.constants.MemberType;


import java.time.LocalDateTime;


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

/*    @Lob // CLOB(Character Large OBject) : 텍스트 형태 파일 크기를 4GB까지 지원
    private String introduction; 뭔지만 알아두자 */

    @Column(updatable = false) // update 가능 여부 -> X로 설정
    @CreationTimestamp
    private LocalDateTime regDt;


    @Column(insertable = false) // insert 가능 여부 -> X 로 설정
    @UpdateTimestamp
    private LocalDateTime modDt;

    // DB에 반영되지 않고 내부에 반영할 때 사용하는 어노테이션 -> 내부에 쓸 목적으로 만드는 것
    // @Transient가 없으면 DB에 반영되는것임 -> 숙지
    @Transient
    private String tmpData;

/*    @Temporal()
    private Date date;
    -> 자바 타임 패키지가 있다보니 temporal을 안쓴다 -> 그냥 참고만하자
    */
}
