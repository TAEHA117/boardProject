package org.koreait.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class BoardData extends Base {
    @Id @GeneratedValue
    private Long seq;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="userNo")
    private Member member;

    @Column(length=30, nullable=false)
    private String poster; // 작성자명

    @Column(nullable = false) // notnull 제약조건 = nullalble
    private String subject; // 제목

    @Lob
    @Column(nullable = false)
    private String content; // 내용

}
