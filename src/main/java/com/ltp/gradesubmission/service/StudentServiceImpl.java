package com.ltp.gradesubmission.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.exception.StudentNotFoundException;
import com.ltp.gradesubmission.repository.CourseRepository;
import com.ltp.gradesubmission.repository.StudentRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {
    
    /*
     * we normally do our business logic here?
     */

    StudentRepository studentRepository;
    CourseRepository courseRepository;

    @Override
    public Student getStudent(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        return unwrapStudent(student, id);
    }

    @Override
    public Student saveStudent(Student student) {
        student.setUniqueId(UUID.randomUUID().toString());
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            studentRepository.deleteById(id);
        } else {
            throw new StudentNotFoundException(id);
        }
    }

    @Override
    public List<Student> getStudents() {
        return (List<Student>) studentRepository.findAll();
    }

    static Student unwrapStudent(Optional<Student> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new StudentNotFoundException(id);
    }

    @Override
    public List<Course> getEnrolledCourse(Long studentId) {
        Optional<Student> tempStudent = studentRepository.findById(studentId);
        Student student = unwrapStudent(tempStudent, studentId);

        return student.getCourses();
    }
}