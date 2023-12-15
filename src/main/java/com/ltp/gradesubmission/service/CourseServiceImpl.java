package com.ltp.gradesubmission.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.exception.CourseEnrollmentException;
import com.ltp.gradesubmission.exception.CourseNotFoundException;
import com.ltp.gradesubmission.repository.CourseRepository;
import com.ltp.gradesubmission.repository.StudentRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {
    CourseRepository courseRepository;
    StudentRepository studentRepository;

    @Override
    public Course getCourse(Long id) {
        Optional<Course> course = courseRepository.findById(id);
        return unwrapCourse(course, id);
    }

    @Override
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Long id) {
        Optional<Course> course = courseRepository.findById(id);

        if (course.isPresent()) {
            courseRepository.deleteById(id);
        } else {
            throw new CourseNotFoundException(id);
        }
    }

    @Override
    public List<Course> getCourses() {
        return (List<Course>) courseRepository.findAll();
    }

    @Override
    public Course enrollStudentToCourse(Long studentId, Long courseId) {
        Optional<Student> tempStudent = studentRepository.findById(studentId);
        Optional<Course> tempCourse = courseRepository.findById(courseId);
        Student student = StudentServiceImpl.unwrapStudent(tempStudent, studentId);
        Course course = unwrapCourse(tempCourse, courseId);

        course.getStudents().add(student);

        return courseRepository.save(course);
    }

    static Course unwrapCourse(Optional<Course> entity, Long id) {
        // we receive the Id beceause we want to tell the consumer
        //  if the id is not found / not exist
        if (entity.isPresent())
            return entity.get();
        else
            throw new CourseNotFoundException(id);
    }

    @Override
    public void dropStudentFromCourse(Long studentId, Long courseId) {
        Optional<Student> tempStudent = studentRepository.findById(studentId);
        Optional<Course> tempCourse = courseRepository.findById(courseId);

        // two lines below will throw exception if one of the following does not exist
        //  1. studentNotFoundException
        //  2. courseNotFoundException
        Student student = StudentServiceImpl.unwrapStudent(tempStudent, studentId);
        Course course = unwrapCourse(tempCourse, courseId);

        if(course.getStudents().contains(student)){
            course.getStudents().remove(student);
            courseRepository.save(course);
        } else {
            throw new CourseEnrollmentException(studentId, courseId);
        }
    }

    @Override
    public Set<Student> getEnrolledStudent(Long courseId) {
        Optional<Course> tempCourse = courseRepository.findById(courseId);
        Course course = unwrapCourse(tempCourse, courseId);

        return (Set<Student>) course.getStudents();
    }
}
