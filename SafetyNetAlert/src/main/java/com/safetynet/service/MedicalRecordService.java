package com.safetynet.service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.safetynet.exception.MedicalRecordAlreadyExistException;
import com.safetynet.exception.MedicalRecordBadRequestException;
import com.safetynet.exception.MedicalRecordNotFoundException;
import com.safetynet.model.MedicalRecord;
import com.safetynet.repository.MedicalRecordRepository;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;

    Logger logger = LoggerFactory.getLogger(MedicalRecordService.class);

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public Set<MedicalRecord> getAllMedicalRecords() {
        Set<MedicalRecord> medicalRecords = medicalRecordRepository.getAll();
        logger.debug("Medical records found : " + medicalRecords.toString());
        return medicalRecords;
    }

    public void saveMedicalRecord(MedicalRecord medicalRecord) throws MedicalRecordAlreadyExistException {
        Optional<MedicalRecord> existingMedicalRecordForPatient = medicalRecordRepository
                .findByPatientName(medicalRecord.getFirstName(), medicalRecord.getLastName());
        if (existingMedicalRecordForPatient.isPresent()) {
            throw new MedicalRecordAlreadyExistException(medicalRecord);
        }
        medicalRecordRepository.save(medicalRecord);
        logger.debug("Medical record saved : " + medicalRecord.toString());
    }

    public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) throws MedicalRecordNotFoundException {
        Optional<MedicalRecord> medicalRecordToUpdate = medicalRecordRepository
                .findByPatientName(medicalRecord.getFirstName(), medicalRecord.getLastName());
        if (medicalRecordToUpdate.isPresent()) {
            logger.debug("Medical record found : " + medicalRecordToUpdate.get().toString());
            MedicalRecord _medicalRecordToUpdate = medicalRecordToUpdate.get();
            medicalRecordRepository.remove(_medicalRecordToUpdate);
            medicalRecordRepository.save(medicalRecord);
            logger.debug("Medical record updated : " + medicalRecord.toString());
            return _medicalRecordToUpdate;
        }
        throw new MedicalRecordNotFoundException(medicalRecord);
    }

    public void removeMedicalRecord(MedicalRecord medicalRecord)
            throws MedicalRecordNotFoundException, MedicalRecordBadRequestException {
        if (Objects.nonNull(medicalRecord.getFirstName()) && Objects.nonNull(medicalRecord.getLastName())) {
            MedicalRecord medicalRecordToRemove = this.getRecord(medicalRecord.getFirstName(),
                    medicalRecord.getLastName());
            medicalRecordRepository.remove(medicalRecordToRemove);
            return;
        }
        throw new MedicalRecordBadRequestException();

    }

    public MedicalRecord getRecord(String firstName, String lastName) throws MedicalRecordNotFoundException {
        Optional<MedicalRecord> medicalRecord = medicalRecordRepository
                .findByPatientName(firstName, lastName);
        if (medicalRecord.isEmpty()) {
            throw new MedicalRecordNotFoundException(
                    MedicalRecord.builder().firstName(firstName).lastName(lastName).build());
        }
        logger.debug("Medical record found : " + medicalRecord.get().toString());
        return medicalRecord.get();
    }
}
