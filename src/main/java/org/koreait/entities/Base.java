package org.koreait.entities;

        import jakarta.persistence.Column;
        import jakarta.persistence.EntityListeners;
        import jakarta.persistence.MappedSuperclass;
        import lombok.Getter;
        import lombok.Setter;
        import org.springframework.data.annotation.CreatedDate;
        import org.springframework.data.annotation.LastModifiedDate;
        import org.springframework.data.jpa.domain.support.AuditingEntityListener;

        import java.time.LocalDateTime;

@Getter @Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Base {
    @Column(updatable = false)
    @CreatedDate // 엔티티에 상태에따라 추가되는 어노테이션
    private LocalDateTime createdAt; // 등록일자


    @Column(insertable = false)
    @LastModifiedDate // 엔티티에 상태에따라 추가되는 어노테이션
    private LocalDateTime modifiedAt; // 수정일자
}