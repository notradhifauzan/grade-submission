package com.ltp.gradesubmission.exception;

public class InvalidDateException extends RuntimeException {
    public InvalidDateException(){
        super("The date is invalid");
    }
}
