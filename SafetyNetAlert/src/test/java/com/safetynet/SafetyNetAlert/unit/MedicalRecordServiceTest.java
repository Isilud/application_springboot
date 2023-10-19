package com.safetynet.SafetyNetAlert.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.exception.MedicalRecordAlreadyExistException;
import com.safetynet.exception.MedicalRecordBadRequestException;
import com.safetynet.exception.MedicalRecordNotFoundException;
import com.safetynet.model.MedicalRecord;
import com.safetynet.repository.MedicalRecordRepository;
import com.safetynet.service.MedicalRecordService;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    private MedicalRecordService medicalRecordService;

    public MedicalRecord defaultMedicalRecord() {
        return MedicalRecord.builder()
                .firstName("firstName")
                .lastName("lastName")
                .birthdate("01/02/03")
                .medication(new HashSet<>())
                .allergies(new HashSet<>())
                .build();
    }

    @BeforeEach
    public void clear() {
        medicalRecordService = new MedicalRecordService(medicalRecordRepository);
    }

    @Test
    public void testSaveMedicalRecord() throws MedicalRecordAlreadyExistException {
        when(medicalRecordRepository.findByPatientName(defaultMedicalRecord().getFirstName(), defaultMedicalRecord().getLastName())).thenReturn(Optional.empty());
        medicalRecordService.saveMedicalRecord(defaultMedicalRecord());
        verify(medicalRecordRepository).save(defaultMedicalRecord());
    }

    @Test
    public void testSaveExistingMedicalRecord() {
        when(medicalRecordRepository.findByPatientName(defaultMedicalRecord().getFirstName(), defaultMedicalRecord().getLastName())).thenReturn(Optional.of(defaultMedicalRecord()));
        assertThrows(MedicalRecordAlreadyExistException.class, () -> medicalRecordService.saveMedicalRecord(defaultMedicalRecord()));
    }

    @Test
    public void testGetMedicalRecords() {
        Set<MedicalRecord> expectedList = new HashSet<MedicalRecord>();
        expectedList.add(defaultMedicalRecord());
        when(medicalRecordRepository.getAll()).thenReturn(expectedList);
        Set<MedicalRecord> registeredMedicalRecords = medicalRecordService.getAllMedicalRecords();
        assertEquals(registeredMedicalRecords, expectedList);
    }

    @Test
    public void testUpdateMedicalRecord() throws MedicalRecordNotFoundException {
        MedicalRecord initialMedicalRecord = defaultMedicalRecord();
        MedicalRecord updatedMedicalRecord = defaultMedicalRecord();
        Set<String> medications = new HashSet<>();
        medications.add("Doliprane");
        updatedMedicalRecord.setMedication(medications);
        when(medicalRecordRepository.findByPatientName(initialMedicalRecord.getFirstName(),
                initialMedicalRecord.getLastName()))
                .thenReturn(Optional.of(initialMedicalRecord));
        medicalRecordService.updateMedicalRecord(updatedMedicalRecord);
        verify(medicalRecordRepository).remove(initialMedicalRecord);
        verify(medicalRecordRepository).save(updatedMedicalRecord);
    }

    @Test
    public void testUpdateMedicalRecordNotFound() {
        when(medicalRecordRepository.findByPatientName(defaultMedicalRecord().getFirstName(), defaultMedicalRecord().getLastName()))
                .thenReturn(Optional.empty());
        assertThrows(MedicalRecordNotFoundException.class, () -> medicalRecordService.updateMedicalRecord(defaultMedicalRecord()));
    }

    @Test
    public void testDeleteMedicalRecord() throws MedicalRecordNotFoundException, MedicalRecordBadRequestException {
        when(medicalRecordRepository.findByPatientName(defaultMedicalRecord().getFirstName(), defaultMedicalRecord().getLastName()))
                .thenReturn(Optional.of(defaultMedicalRecord()));
        medicalRecordService.removeMedicalRecord(defaultMedicalRecord());
        verify(medicalRecordRepository).remove(defaultMedicalRecord());
    }

    @Test
    public void testDeleteMedicalRecordNotFound() {
        when(medicalRecordRepository.findByPatientName(defaultMedicalRecord().getFirstName(), defaultMedicalRecord().getLastName()))
                .thenReturn(Optional.empty());
        assertThrows(MedicalRecordNotFoundException.class,
                () -> medicalRecordService.removeMedicalRecord(defaultMedicalRecord()));
    }

    @Test
    public void testDeleteMedicalRecordBadRequest() {
        MedicalRecord medicalRecordToRemove = MedicalRecord.builder().build();
        assertThrows(MedicalRecordBadRequestException.class,
                () -> medicalRecordService.removeMedicalRecord(medicalRecordToRemove));
    }

}
