package com.safetynet.service;

import java.util.Optional;
import java.util.Set;

import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import com.safetynet.model.MedicalRecord;
import com.safetynet.repository.MedicalRecordRepository;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public Set<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordRepository.getAll();
    }

    public void saveMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecordRepository.save(medicalRecord);
    }

    public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) {
        Optional<MedicalRecord> medicalRecordToUpdate = medicalRecordRepository
                .findByPatientName(medicalRecord.getFirstName(), medicalRecord.getLastName());
        if (medicalRecordToUpdate.isPresent()) {
            MedicalRecord _medicalRecordToUpdate = medicalRecordToUpdate.get();
            _medicalRecordToUpdate.setBirthDate(medicalRecord.getBirthDate());
            _medicalRecordToUpdate.setAllergies(medicalRecord.getAllergies());
            _medicalRecordToUpdate.setMedication(medicalRecord.getMedication());
            return _medicalRecordToUpdate;
        }
        throw new ObjectNotFoundException(
                "No medical record with name: " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName()
                        + " found.",
                medicalRecordToUpdate);
    }

    public void removeMedicalRecord(String firstName, String lastName) {
        Optional<MedicalRecord> medicalRecordToRemove = medicalRecordRepository.findByPatientName(firstName, lastName);
        if (medicalRecordToRemove.isPresent()) {
            medicalRecordRepository.remove(medicalRecordToRemove.get());
        }
        throw new ObjectNotFoundException(
                "No medical record with name: " + firstName + " " + lastName + " found.",
                medicalRecordToRemove);
    }
}
