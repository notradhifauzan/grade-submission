package com.ltp.gradesubmission.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ltp.gradesubmission.entity.ImageData;
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.exception.ImageExceedSizeException;
import com.ltp.gradesubmission.exception.ImageNotFoundException;
import com.ltp.gradesubmission.repository.FileRepository;
import com.ltp.gradesubmission.repository.StudentRepository;
import com.ltp.gradesubmission.utils.ImageUtils;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public ImageData uploadImage(MultipartFile file, Long studentId) throws IOException {
        Optional<Student> studentTemp = studentRepository.findById(studentId);
        Student student = StudentServiceImpl.unwrapStudent(studentTemp, studentId);

        long maxSizeBytes = 5 * 1024 * 1024;

        if (file.getSize() > maxSizeBytes) {
            throw new ImageExceedSizeException();
        }

        ImageData imageData = fileRepository.save(ImageData.builder()
                .name(student.getUniqueId())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes()))
                .student(student)
                .size(file.getSize()).build());

        
        imageData.setImageData(ImageUtils.decompressImage(file.getBytes()));

        return imageData;
    }

    @Override // need to get the UUID
    public byte[] downloadImage(Long studentId) {
        // file name must consist of original file name and unique student id
        Optional<Student> tempStudent = studentRepository.findById(studentId);
        Student student = StudentServiceImpl.unwrapStudent(tempStudent, studentId);
        Optional<ImageData> dbImageData = fileRepository.findByName(student.getUniqueId());

        ImageData imageData = unwrappedImage(dbImageData, student.getUniqueId());

        byte[] image = ImageUtils.decompressImage(imageData.getImageData());

        return image;
    }

    public static ImageData unwrappedImage(Optional<ImageData> image, String filename) {
        if (image.isPresent()) {
            return image.get();
        } else {
            throw new ImageNotFoundException(filename);
        }
    }

    @Override
    public ImageData updateImage(MultipartFile file, Long studentId) throws IOException {
        // file name must consist of original file name and unique student id
        Optional<Student> tempStudent = studentRepository.findById(studentId);
        Student student = StudentServiceImpl.unwrapStudent(tempStudent, studentId);
        Optional<ImageData> dbImageData = fileRepository.findByName(student.getUniqueId());

        ImageData imageData = unwrappedImage(dbImageData, student.getUniqueId());

        long maxSizeBytes = 5 * 1024 * 1024;

        if (file.getSize() > maxSizeBytes) {
            throw new ImageExceedSizeException();
        }

        imageData.setImageData(ImageUtils.compressImage(file.getBytes()));
        imageData.setSize(file.getSize());
        imageData.setType(file.getContentType());

        fileRepository.save(imageData);

        imageData.setImageData(ImageUtils.decompressImage(file.getBytes()));

        return imageData;
    }

}
