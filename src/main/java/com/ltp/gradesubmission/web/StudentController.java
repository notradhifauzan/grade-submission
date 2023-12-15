package com.ltp.gradesubmission.web;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.entity.ImageData;
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.exception.ErrorResponse;
import com.ltp.gradesubmission.service.FileService;
import com.ltp.gradesubmission.service.StudentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "Student Controller", description = "CRUD operation for students")
@AllArgsConstructor
@RestController
@RequestMapping("/student")
public class StudentController {

    FileService fileService;
    StudentService studentService;

    @PostMapping(value = "/uploadPhoto")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file,
            @RequestParam("student_id") Long studentId) throws IOException {
        ImageData uploadImage = fileService.uploadImage(file, studentId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    @GetMapping(value = "/getPhoto/{studentId}")
    public ResponseEntity<?> getImage(@PathVariable Long studentId) {
        byte[] imageData = fileService.downloadImage(studentId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

    @PutMapping(value = "/updatePhoto")
    public ResponseEntity<?> updateImage(@RequestParam("image") MultipartFile file,
            @RequestParam("student_id") Long studentId) throws IOException {
        ImageData updateImage = fileService.updateImage(file, studentId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(updateImage);
    }

    @Operation(summary = "Retrieve a student", description = "return a single student object based on ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Student doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful retrieval of student", content = @Content(schema = @Schema(implementation = Student.class))),
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        /*
         * you dont need to implement error handling here if requested student id is not
         * found
         * everything is already implemented in StudentNotFoundException
         */
        return new ResponseEntity<>(studentService.getStudent(id), HttpStatus.OK);
    }

    @Operation(summary = "Add new student", description = "Save new student object")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad request, some fields are missing", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful creation of new student", content = @Content(schema = @Schema(implementation = Student.class))),
    })
    @PostMapping
    public ResponseEntity<Student> saveStudent(@Valid @RequestBody Student student) {
        studentService.saveStudent(student);
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete existing student", description = "Delete a single student in student database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Not found, cannot delete non-existing resource", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "204", description = "Successful deletion of student", content = @Content)
    })
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Retrieve all students", description = "Retrieve all existing students in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of all students", content = @Content(schema = @Schema(implementation = Student.class))),
            @ApiResponse(responseCode = "204", description = "No content", content = @Content)
    })
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Student>> getStudents() {
        return new ResponseEntity<>(studentService.getStudents(), HttpStatus.OK);
    }

    @GetMapping(value = "/enrolledCourse/{studentId}")
    public ResponseEntity<List<Course>> getEnrolledCourse(@PathVariable Long studentId) {
        return new ResponseEntity<List<Course>>(studentService.getEnrolledCourse(studentId), HttpStatus.OK);
    }

}
