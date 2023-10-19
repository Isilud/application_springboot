package com.safetynet.exception;

import com.safetynet.model.MedicalRecord;

public class MedicalRecordAlreadyExistException extends Exception {

    public MedicalRecordAlreadyExistException(MedicalRecord medicalRecord) {
        super("Medical Record for patient " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName()
                + " already exist.");
    }

}
