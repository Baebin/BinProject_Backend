package com.piebin.binproject.model.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private String email;

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    @Builder.Default
    List<Notice> notices = new ArrayList<>();

    // Permission
    @OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @Builder.Default
    private List<AccountPermission> permissions = new ArrayList<>();

    // OAuth2
    private String provider;
    @Column(name = "provider_id")
    private String providerId;

    // Etc
    @CreatedDate
    @Column(name = "reg_date")
    private LocalDateTime regDate;
}
