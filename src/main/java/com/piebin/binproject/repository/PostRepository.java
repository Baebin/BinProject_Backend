package com.piebin.binproject.repository;

import com.piebin.binproject.entity.State;
import com.piebin.binproject.model.domain.Post;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByIdxAndState(Long idx, State state);

    List<Post> findAllByStateOrderByRegDateDesc(PageRequest pageRequest, State state);
    List<Post> findAllByTitleContainsAndStateOrderByRegDateDesc(PageRequest pageRequest, String title, State state);
    List<Post> findAllByTextContainsAndStateOrderByRegDateDesc(PageRequest pageRequest, String text, State state);
    List<Post> findAllByTitleContainsOrTextContainsAndStateOrderByRegDateDesc(PageRequest pageRequest, String title, String text, State state);
}
