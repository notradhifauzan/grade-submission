package com.ltp.gradesubmission.service;

import java.util.List;
import java.util.Set;

import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.entity.Student;

public interface CourseService {
    Course getCourse(Long id);
    Course saveCourse(Course course);
    Set<Student> getEnrolledStudent(Long courseId);
    Course enrollStudentToCourse(Long studentId,Long courseId);
    void dropStudentFromCourse(Long studentId,Long courseId);
    void deleteCourse(Long id);
    List<Course> getCourses();
}