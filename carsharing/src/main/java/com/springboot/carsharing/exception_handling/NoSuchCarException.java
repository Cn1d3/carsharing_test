package com.springboot.carsharing.exception_handling;

public class NoSuchCarException extends RuntimeException{
    public NoSuchCarException(String message) {
        super(message);
    }
}
