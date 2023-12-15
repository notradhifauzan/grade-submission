package com.ltp.gradesubmission.exception;

public class ImageExceedSizeException extends RuntimeException{
    public ImageExceedSizeException(){
        super("Image size should not exceed 5MB");
    }
    
}
