package com.safetynet.repository;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.safetynet.model.MedicalRecord;

@Repository
public class MedicalRecordRepository {

    private static Set<MedicalRecord> medicalRecords = new HashSet<MedicalRecord>();

    public Set<MedicalRecord> getAll() {
        return medicalRecords;
    }

    public boolean save(MedicalRecord medicalRecord) {
        return MedicalRecordRepository.medicalRecords.add(medicalRecord);
    }

    public Optional<MedicalRecord> findByPatientName(String firstName, String lastName) {
        return medicalRecords.stream()
                .filter(m -> Objects.nonNull(firstName) && Objects.nonNull(lastName)
                        && Objects.equals(m.getFirstName(), firstName) && Objects.equals(m.getLastName(), lastName))
                .findFirst();
    }

    public boolean remove(MedicalRecord medicalRecord) {
        return MedicalRecordRepository.medicalRecords.remove(medicalRecord);
    }
}
