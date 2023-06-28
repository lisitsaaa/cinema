package com.example.cinema.controller.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

public final class Validator {
    public static boolean getValidationResult(BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            Map<String, String > errors = new HashMap<>();
            for(FieldError fieldError : bindingResult.getFieldErrors()){
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            System.out.println(errors);
            return false;
        }
        return true;
    }
}
