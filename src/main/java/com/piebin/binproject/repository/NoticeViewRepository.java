package com.piebin.binproject.repository;

import com.piebin.binproject.model.domain.NoticeView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface NoticeViewRepository extends JpaRepository<NoticeView, Long> {
    boolean existsByIpAndRegDateAfter(String ip, LocalDateTime dateTime);
}
