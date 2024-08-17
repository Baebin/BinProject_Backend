package com.piebin.binproject.repository;

import com.piebin.binproject.model.domain.Post;
import com.piebin.binproject.model.domain.PostView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PostViewRepository extends JpaRepository<PostView, Long> {
    boolean existsByPostAndIpAndRegDateAfter(Post post, String ip, LocalDateTime dateTime);
}
