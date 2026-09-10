package com.example.loan;

public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}
