package springdata.datajpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public class JpaBaseEntity {

    @Column(updatable = false)
    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    // @PostPersist persist 후에 실행됨
    @PrePersist //persist 전에 실행됨
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdDate = now;
        updatedDate = now;
        System.out.println(" ==================== persist ==================== ");
    }

    // @PostUpdate update 후에 실행됨
    @PreUpdate //update 전에 실행됨
    public void preUpdate() {
        updatedDate = LocalDateTime.now();
        System.out.println(" ===================== update =================== ");
    }
}
