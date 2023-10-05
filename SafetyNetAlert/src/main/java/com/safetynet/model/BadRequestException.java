package com.safetynet.model;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }

}