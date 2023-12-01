package com.safetynet.exception;

public class MedicalRecordBadRequestException extends Exception {

    public MedicalRecordBadRequestException() {
        super("Missing argument for Medical Record.");
    }

}