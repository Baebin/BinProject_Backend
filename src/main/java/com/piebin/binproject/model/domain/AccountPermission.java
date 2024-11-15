package com.piebin.binproject.model.domain;

import com.piebin.binproject.entity.Permission;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AccountPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne
    private Account account;

    @Enumerated(EnumType.STRING)
    private Permission permission;

    // Etc
    @CreatedDate
    @Column(name = "reg_date")
    private LocalDateTime regDate;
}
