package com.ltp.gradesubmission.validation;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ScoreValidator implements ConstraintValidator<Score, String> {

    List<String> scores = Arrays.asList(
            "S","A+", "A", "A-",
            "B+", "B", "B-",
            "C+", "C", "C-",
            "D+", "D", "D-",
            "F");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null) return false;
        if(scores.contains(value)){
            return true;
        } else {
            return false;
        }
    }

}
