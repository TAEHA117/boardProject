package org.koreait.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor @AllArgsConstructor
public class BoardData extends BaseMember {
    @Id
    @GeneratedValue
    private Long seq;

    @Column(length=100, nullable = false)
    private String subject;

    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY) // 3. 다대일(N:1) : @ManyToOne -> Member
    @JoinColumn(name="userNo") // JoinColumn으로 name의 set값을 userNo로 변경함 -> 내가 지정해 줄 수 있다는 말이다.
    private Member member;


}