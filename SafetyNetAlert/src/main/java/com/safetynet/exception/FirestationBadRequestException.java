package com.safetynet.exception;

public class FirestationBadRequestException extends Exception {

    public FirestationBadRequestException() {
        super("Missing argument for Firestation.");
    }

}