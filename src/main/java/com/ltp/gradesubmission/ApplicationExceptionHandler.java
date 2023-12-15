package com.ltp.gradesubmission;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ltp.gradesubmission.exception.*;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
    /*
     * trying to access non-existing resource in the database
     */
    @ExceptionHandler({StudentNotFoundException.class,CourseNotFoundException.class,
        GradeNotFoundException.class,CourseEnrollmentException.class,ImageNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(RuntimeException e){
        ErrorResponse error = new ErrorResponse(Arrays.asList(e.getMessage()));
        return new ResponseEntity<Object>(error, HttpStatus.NOT_FOUND);
    }

    /*
     * trying to manipulate non-existing resource in the database
     */
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Object> handleDataAccessException(EmptyResultDataAccessException e){
        ErrorResponse error = new ErrorResponse(Arrays.asList("Cannot delete non-existing resources"));
        return new ResponseEntity<Object>(error, HttpStatus.NOT_FOUND);
    }

    /*
     * exception handler for file size
     */
    @ExceptionHandler({ImageExceedSizeException.class, MaxUploadSizeExceededException.class})
    public ResponseEntity<Object> handleFileSizeException(RuntimeException e){
        ErrorResponse error = new ErrorResponse(Arrays.asList(e.getMessage()));
        return new ResponseEntity<Object>(error,HttpStatus.BAD_REQUEST);
    }

    /*
     * trying to create duplicate resources
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException e){
        ErrorResponse error = new ErrorResponse(Arrays.asList("Data Integrity Violation: We cannot process your request"));
        return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
    }
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        System.out.println("method argument not valid exception occured!");
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error)->errors.add(error.getDefaultMessage()));
        return new ResponseEntity<>(new ErrorResponse(errors),HttpStatus.BAD_REQUEST);
    }
}
