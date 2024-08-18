package com.piebin.binproject.model.domain;

import com.piebin.binproject.entity.State;
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
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(unique = true, nullable = false)
    private String uuid;

    @ManyToOne
    private Account author;

    @Column(length = 100)
    private String title;
    @Column(length = 50000)
    private String text;

    @Builder.Default
    private Integer files = 0;

    @OneToMany(mappedBy = "notice")
    @Builder.Default
    private List<NoticeView> views = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private State state = State.ENABLED;

    @CreatedDate
    @Column(name = "reg_date")
    private LocalDateTime regDate;
}
