package com.ltp.gradesubmission.exception;

public class ImageNotFoundException extends RuntimeException{
    public ImageNotFoundException(String filename){
        super("no resources found");
    }
}
