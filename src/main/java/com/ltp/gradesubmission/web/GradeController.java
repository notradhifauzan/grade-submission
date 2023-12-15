package com.ltp.gradesubmission.web;

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
import org.springframework.web.bind.annotation.RestController;

import com.ltp.gradesubmission.entity.Grade;
import com.ltp.gradesubmission.exception.ErrorResponse;
import com.ltp.gradesubmission.service.GradeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "Grade controller", description = "CRUD operations for grade")
@AllArgsConstructor
@RestController
@RequestMapping("/grade")
public class GradeController {

    GradeService gradeService;

    @Operation(summary = "Retrieve grade with given studentId and courseId", description = "return the grade of a particular given courseId and studentId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Not found. No matching grade with given studentId and courseId", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful retrieval of grade with given studentId and courseId", content = @Content(schema = @Schema(implementation = Grade.class))),
    })
    @GetMapping(value = "/student/{studentId}/course/{courseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Grade> getGrade(@PathVariable Long studentId, @PathVariable Long courseId) {
        return new ResponseEntity<>(gradeService.getGrade(studentId, courseId), HttpStatus.OK);
    }

    @Operation(summary = "Add a new grade", description = "Create a new grade with specified score, studentId and courseId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad request. Cannot insert duplicate grade", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "201", description = "Successful creation of grade with given score, studentId and courseId", content = @Content(schema = @Schema(implementation = Grade.class))),
    })
    @PostMapping(value = "/student/{studentId}/course/{courseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Grade> saveGrade(@Valid @RequestBody Grade grade, @PathVariable Long studentId,
            @PathVariable Long courseId) {
        gradeService.saveGrade(grade, studentId, courseId);
        return new ResponseEntity<>(grade, HttpStatus.CREATED);
    }

    @Operation(summary = "Update grade with given studentId and courseId", description = "Update existing grade based on given studentId and courseId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request. Cannot modify non-existing resource", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successfully update existing grade", content = @Content(schema = @Schema(implementation = Grade.class))),
            @ApiResponse(responseCode = "404", description = "Not found. Cannot find grade with matching studentId and courseId", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PutMapping(value = "/student/{studentId}/course/{courseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Grade> updateGrade(@Valid @RequestBody Grade grade, @PathVariable Long studentId,
            @PathVariable Long courseId) {
        return new ResponseEntity<>(gradeService.updateGrade(grade.getScore(), studentId, courseId), HttpStatus.OK);
    }

    @Operation(summary = "Delete grade with given studentId and courseId", description = "Delete existing grade based on given studentId and courseId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request. Cannot modify non-existing resource", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successfully delete existing grade", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found. Cannot find grade with matching studentId and courseId", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @DeleteMapping(value = "/student/{studentId}/course/{courseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> deleteGrade(@PathVariable Long studentId, @PathVariable Long courseId) {
        gradeService.deleteGrade(studentId, courseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Retrieve all grades based on studentId", description = "Return a list of grades correspond to the given studentId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrive all grades based on studentId", content = @Content(schema = @Schema(implementation = Grade.class))),
            @ApiResponse(responseCode = "404", description = "Not found. Cannot find grade with matching studentId", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping(value = "/student/{studentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Grade>> getStudentGrades(@PathVariable Long studentId) {
        return new ResponseEntity<>(gradeService.getStudentGrades(studentId), HttpStatus.OK);
    }

    @Operation(summary = "Retrieve all grades based on courseId", description = "Return a list of grades correspond to the given courseId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrive all grades based on courseId", content = @Content(schema = @Schema(implementation = Grade.class))),
            @ApiResponse(responseCode = "404", description = "Not found. Cannot find grade with matching courseId", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping(value="/course/{courseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Grade>> getCourseGrades(@PathVariable Long courseId) {
        return new ResponseEntity<>(gradeService.getCourseGrades(courseId), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Grade>> getGrades() {
        return new ResponseEntity<>(gradeService.getAllGrades(), HttpStatus.OK);
    }

}
