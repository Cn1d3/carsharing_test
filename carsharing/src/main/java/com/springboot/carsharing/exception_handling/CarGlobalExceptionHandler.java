package com.springboot.carsharing.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CarGlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<CarIncorrectData> handleException(NoSuchCarException e){
        CarIncorrectData data = new CarIncorrectData();
        data.setInfo(e.getMessage());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<CarIncorrectData> handleException(Exception e){
        CarIncorrectData data = new CarIncorrectData();
        data.setInfo(e.getMessage());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}
