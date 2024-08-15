package com.piebin.binproject.repository;

import com.piebin.binproject.model.domain.Notice;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Optional<Notice> findByIdx(Long idx);

    List<Notice> findAllByOrderByRegDateDesc(PageRequest pageRequest);
    List<Notice> findAllByTitleContainsOrderByRegDateDesc(PageRequest pageRequest, String title);
    List<Notice> findAllByTextContainsOrderByRegDateDesc(PageRequest pageRequest, String text);
}
