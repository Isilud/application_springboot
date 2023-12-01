package com.safetynet.controller;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.exception.MedicalRecordAlreadyExistException;
import com.safetynet.exception.MedicalRecordBadRequestException;
import com.safetynet.exception.MedicalRecordNotFoundException;
import com.safetynet.model.MedicalRecord;
import com.safetynet.service.MedicalRecordService;

@RestController
public class MedicalRecordController {

    @Autowired
    MedicalRecordService medicalRecordService;

    Logger logger = LoggerFactory.getLogger(MedicalRecordController.class);

    @PostMapping("/medicalRecord")
    @ResponseStatus(HttpStatus.CREATED)
    public MedicalRecord createMedicalRecord(@RequestBody MedicalRecord medicalRecord)
            throws MedicalRecordAlreadyExistException {
        logger.info("Creating new medical record : " + medicalRecord.toString());
        medicalRecordService.saveMedicalRecord(medicalRecord);
        return medicalRecord;
    }

    @GetMapping("/medicalRecord")
    @ResponseStatus(HttpStatus.OK)
    public Set<MedicalRecord> getAllMedicalRecords() {
        logger.info("Fetching all medical records");
        return medicalRecordService.getAllMedicalRecords();
    }

    @PutMapping("/medicalRecord")
    @ResponseStatus(HttpStatus.OK)
    public MedicalRecord updateMedicalRecord(@RequestBody MedicalRecord medicalRecord)
            throws MedicalRecordNotFoundException {
        logger.info("Updating medical record : " + medicalRecord.toString());
        return medicalRecordService.updateMedicalRecord(medicalRecord);
    }

    @DeleteMapping("/medicalRecord")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMedicalRecord(@RequestBody MedicalRecord medicalRecord)
            throws MedicalRecordNotFoundException, MedicalRecordBadRequestException {
        logger.info("Deleting medical record : " + medicalRecord.toString());
        medicalRecordService.removeMedicalRecord(medicalRecord);
        return;
    }

}
