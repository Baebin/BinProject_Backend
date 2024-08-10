package com.piebin.binproject.model.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Image {
    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String path;
    private String name;
    private String ext;

    @CreatedDate
    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "edit_date")
    private LocalDateTime editDate;
}
