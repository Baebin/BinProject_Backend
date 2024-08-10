package com.piebin.binproject.model.domain;

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
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    // Authorization
    @Column(unique = true)
    private String id;

    private String password;

    // Profile
    private String name;

    private String phone;

    @Column(unique = true)
    private String email;

    // Etc
    @CreatedDate
    @Column(name = "reg_date")
    private LocalDateTime regDate;
}
