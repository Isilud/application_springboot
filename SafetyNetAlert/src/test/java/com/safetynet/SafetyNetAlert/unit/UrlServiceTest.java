package com.safetynet.SafetyNetAlert.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.dto.PersonsInCoverageSummaryDTO;
import com.safetynet.exception.FirestationStationNotFoundException;
import com.safetynet.exception.MedicalRecordNotFoundException;
import com.safetynet.model.Firestation;
import com.safetynet.model.MedicalRecord;
import com.safetynet.model.Person;
import com.safetynet.service.FirestationService;
import com.safetynet.service.MedicalRecordService;
import com.safetynet.service.PersonService;
import com.safetynet.service.UrlService;

@ExtendWith(MockitoExtension.class)
public class UrlServiceTest {

    @Mock
    private PersonService personService;
    @Mock
    private FirestationService firestationService;
    @Mock
    private MedicalRecordService medicalRecordService;

    private UrlService urlService;

    public Set<Person> defaultPersonsWithAddress(String address) {
        Set<Person> persons = new HashSet<>();
        persons.add(Person.builder()
                .firstName("John")
                .lastName("Doe")
                .address(address)
                .city("city")
                .zip("1234")
                .phone("0123456")
                .email("JohnDoe")
                .build());
        persons.add(Person.builder()
                .firstName("Jane")
                .lastName("Doe")
                .address(address)
                .city("city")
                .zip("1234")
                .phone("7891011")
                .email("JaneDoe")
                .build());
        persons.add(Person.builder()
                .firstName("Joseph")
                .lastName("Doe")
                .address(address)
                .city("city")
                .zip("1234")
                .phone("None")
                .email("None")
                .build());
        return persons;
    }

    public Set<Firestation> defaultFirestationsWithAddress(String address) {
        Set<Firestation> firestation = new HashSet<>();
        firestation.add(Firestation.builder()
                .address(address)
                .station("1")
                .build());
        return firestation;
    }

    public MedicalRecord adultMedicalRecord = MedicalRecord.builder()
            .firstName("John")
            .lastName("Doe")
            .birthdate("01/02/1994")
            .allergies(new HashSet<>())
            .medication(new HashSet<>())
            .build();

    public MedicalRecord childMedicalRecord = MedicalRecord.builder()
            .firstName("Joseph")
            .lastName("Doe")
            .birthdate("01/02/2017")
            .allergies(new HashSet<>())
            .medication(new HashSet<>())
            .build();

    @BeforeEach
    public void clear() {
        urlService = new UrlService(medicalRecordService, personService, firestationService);
    }

    @Test
    public void getPeopleWithStationCoverage()
            throws FirestationStationNotFoundException, MedicalRecordNotFoundException {
        String address = "validAddress";
        PersonsInCoverageSummaryDTO expectedResponse = PersonsInCoverageSummaryDTO.builder()
                .personsInCoverage(defaultPersonsWithAddress(address))
                .adultCount(2)
                .childrenCount(1)
                .build();
        when(firestationService.findAllByStation("1"))
                .thenReturn(defaultFirestationsWithAddress(address));
        when(personService.getAllPersons())
                .thenReturn(defaultPersonsWithAddress(address));
        when(medicalRecordService.getRecord(anyString(), anyString()))
                .thenReturn(adultMedicalRecord, adultMedicalRecord, childMedicalRecord);
        PersonsInCoverageSummaryDTO response = urlService.getPeopleWithStationCoverage("1");
        assertEquals(expectedResponse.getPersonsInCoverage(), response.getPersonsInCoverage());
        assertEquals(expectedResponse.getAdultCount(), response.getAdultCount());
        assertEquals(expectedResponse.getChildrenCount(), response.getChildrenCount());
    }
}
