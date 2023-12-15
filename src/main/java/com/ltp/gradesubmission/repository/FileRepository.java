package com.ltp.gradesubmission.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ltp.gradesubmission.entity.ImageData;

public interface FileRepository extends JpaRepository<ImageData,Long> {
    Optional<ImageData> findByName(String fileName);
    Optional<ImageData> findImageByStudentId(Long studentId);
}
