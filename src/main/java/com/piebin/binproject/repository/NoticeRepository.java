package com.piebin.binproject.repository;

import com.piebin.binproject.entity.State;
import com.piebin.binproject.model.domain.Notice;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Optional<Notice> findByIdxAndState(Long idx, State state);

    List<Notice> findAllByStateOrderByRegDateDesc(PageRequest pageRequest, State state);
    List<Notice> findAllByTitleContainsAndStateOrderByRegDateDesc(PageRequest pageRequest, String title, State state);
    List<Notice> findAllByTextContainsAndStateOrderByRegDateDesc(PageRequest pageRequest, String text, State state);
    List<Notice> findAllByTitleContainsOrTextContainsAndStateOrderByRegDateDesc(PageRequest pageRequest, String title, String text, State state);
}
