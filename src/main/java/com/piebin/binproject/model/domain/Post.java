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
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(unique = true, nullable = false)
    private String uuid;

    @ManyToOne
    private Account author;

    private String title;
    private String text;

    @Builder.Default
    private Integer files = 0;

    @OneToMany(mappedBy = "post")
    @Builder.Default
    private List<PostComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    @Builder.Default
    private List<PostView> views = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private State state = State.ENABLED;

    @CreatedDate
    @Column(name = "reg_date")
    private LocalDateTime regDate;
}
