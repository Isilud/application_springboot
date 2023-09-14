package com.safetynet.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.safetynet.model.MedicalRecord;

@Service
public class MedicalRecordRepository {

    private static Set<MedicalRecord> MedicalRecords = new HashSet<MedicalRecord>();

    public Set<MedicalRecord> getAll() {
        return MedicalRecords;
    }

    public void save(MedicalRecord medicalRecord) {
        MedicalRecordRepository.MedicalRecords.add(medicalRecord);
    }

    public void remove(MedicalRecord medicalRecord) {
        MedicalRecordRepository.MedicalRecords.remove(medicalRecord);
    }
}
