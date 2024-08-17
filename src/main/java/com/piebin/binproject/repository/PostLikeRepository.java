package com.piebin.binproject.repository;

import com.piebin.binproject.model.domain.Account;
import com.piebin.binproject.model.domain.Post;
import com.piebin.binproject.model.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    void deleteAllByPostAndAccount(Post post, Account account);
}
