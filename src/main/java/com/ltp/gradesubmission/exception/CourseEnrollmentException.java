package com.ltp.gradesubmission.exception;

public class CourseEnrollmentException extends RuntimeException{
    public CourseEnrollmentException(Long studentId,Long courseId){
        super("It seems that the studentId: " + studentId + " does not enroll with this course: " + courseId + " yet.");
    }
}
