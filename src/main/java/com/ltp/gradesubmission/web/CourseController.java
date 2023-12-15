package com.ltp.gradesubmission.web;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.service.CourseService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/course")
public class CourseController {

    CourseService courseService;

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable Long id) {
        return new ResponseEntity<>(courseService.getCourse(id),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Course> saveCourse(@Valid @RequestBody Course course) {
        return new ResponseEntity<>(courseService.saveCourse(course), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Course>> getCourses() {
        return new ResponseEntity<>(courseService.getCourses(),HttpStatus.OK);
    }

    @GetMapping("/enrolled/{courseId}")
    public ResponseEntity<Set<Student>> getEnrolledStudent(@PathVariable Long courseId){
        return new ResponseEntity<Set<Student>>(courseService.getEnrolledStudent(courseId), HttpStatus.OK);
    }

    @PutMapping("/{courseId}/student/{studentId}")
    public ResponseEntity<Course> enrollStudentToCourse(@PathVariable Long courseId,@PathVariable Long studentId){
        return new ResponseEntity<Course>(courseService.enrollStudentToCourse(studentId, courseId),HttpStatus.CREATED);
    }
    
    @DeleteMapping("/{courseId}/student/{studentId}")
    public ResponseEntity<Course> dropStudentFromCourse(@PathVariable Long courseId,@PathVariable Long studentId){
        courseService.dropStudentFromCourse(studentId, courseId);
        return new ResponseEntity<Course>(courseService.getCourse(courseId), HttpStatus.OK);
    }
    

}
