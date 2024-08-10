package com.piebin.binproject.repository;

import com.piebin.binproject.model.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByPathAndName(String path, String name);
}
