package com.safetynet.exception;

import com.safetynet.model.MedicalRecord;

public class MedicalRecordNotFoundException extends Exception {

    public MedicalRecordNotFoundException(MedicalRecord medicalRecord) {
        super("Medical Record for patient " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName()
                + " not found.");
    }

}
