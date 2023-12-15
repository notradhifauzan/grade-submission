package com.ltp.gradesubmission.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.ltp.gradesubmission.entity.ImageData;

public interface FileService {
    // download or display? not sure
    // I think this one is get image, not download
    byte[] downloadImage(Long studentId);
    ImageData updateImage(MultipartFile file,Long studentId) throws IOException;
    ImageData uploadImage(MultipartFile file,Long studentId) throws IOException;
}
