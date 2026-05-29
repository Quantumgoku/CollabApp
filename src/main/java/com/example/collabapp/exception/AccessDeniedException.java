package com.example.collabapp.exception;

public class AccessDeniedException extends RuntimeException{
    public AccessDeniedException(String message){
        super(message);
    }
}
