package com.safetynet.exception;

public class PersonBadRequestException extends Exception {

    public PersonBadRequestException() {
        super("Missing argument for Person.");
    }

}