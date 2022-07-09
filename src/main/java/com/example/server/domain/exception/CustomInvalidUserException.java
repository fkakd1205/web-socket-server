package com.example.server.domain.exception;

public class CustomInvalidUserException extends RuntimeException {

    public CustomInvalidUserException(String arg0) {
        super(arg0);
    }

    public CustomInvalidUserException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }
    
}
