package com.piebin.binproject.repository;

import com.piebin.binproject.entity.State;
import com.piebin.binproject.model.domain.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
    Optional<PostComment> findByIdxAndState(Long idx, State state);
    Optional<PostComment> findByPost_IdxAndIdxAndState(Long postIdx, Long commentIdx, State state);
}
