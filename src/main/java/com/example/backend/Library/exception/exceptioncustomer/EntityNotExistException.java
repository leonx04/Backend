package com.example.backend.Library.exception.exceptioncustomer;

public class EntityNotExistException extends RuntimeException{
    public EntityNotExistException(String message) {
        super(message);
    }
    public EntityNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
