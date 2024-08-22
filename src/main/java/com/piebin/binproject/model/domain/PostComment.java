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
public class PostComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne
    private Post post;

    @ManyToOne
    private Account author;

    @Column(length = 1000)
    private String comment;

    @JoinColumn(name = "parent_comment")
    @ManyToOne
    private PostComment parentComment;

    @OneToMany(mappedBy = "parentComment")
    @Builder.Default
    private List<PostComment> childComments = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private State state = State.ENABLED;

    @CreatedDate
    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @Column(name = "edited_date")
    private LocalDateTime editedDate;
}
