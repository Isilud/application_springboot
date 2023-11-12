package com.safetynet.SafetyNetAlert.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.dto.ChildrenWithAddressDTO;
import com.safetynet.dto.FireInformationDTO;
import com.safetynet.dto.PersonsInCoverageSummaryDTO;
import com.safetynet.exception.FirestationAddressNotFoundException;
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
                                .lastName("Dud")
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
                                .phone("12131415")
                                .email("None")
                                .build());
                return persons;
        }

        public Firestation defaultFirestationWithAddress(String address) {
                Firestation firestation = (Firestation.builder()
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
                Set<Firestation> firestationsSet = new HashSet<Firestation>();
                firestationsSet.add(defaultFirestationWithAddress(address));
                when(firestationService.findAllByStation("1"))
                                .thenReturn(firestationsSet);
                when(personService.getAllPersons())
                                .thenReturn(defaultPersonsWithAddress(address));
                when(medicalRecordService.getRecord(anyString(), anyString()))
                                .thenReturn(adultMedicalRecord, adultMedicalRecord, childMedicalRecord);
                PersonsInCoverageSummaryDTO response = urlService.getPeopleWithStationCoverage("1");
                assertEquals(expectedResponse.getPersonsInCoverage(), response.getPersonsInCoverage());
                assertEquals(expectedResponse.getAdultCount(), response.getAdultCount());
                assertEquals(expectedResponse.getChildrenCount(), response.getChildrenCount());
        }

        @Test
        public void getChildrenAtAddress() throws MedicalRecordNotFoundException {
                String address = "validAddress";
                when(personService.getAllPersonsWithAddress(address))
                                .thenReturn(defaultPersonsWithAddress(address));
                when(medicalRecordService.getRecord(anyString(), anyString()))
                                .thenReturn(childMedicalRecord, childMedicalRecord, adultMedicalRecord);
                Set<ChildrenWithAddressDTO> response = urlService.getChildrenAtAddress(address);
                assertEquals(2, response.size());
                for (ChildrenWithAddressDTO child : response) {
                        assertEquals(2, child.getFamily().size());
                        List<String> firstNames = child.getFamily().stream().map(p -> p.getFirstName()).toList();
                        assertFalse(firstNames.contains(child.getFirstName()));
                }
                ;
        }

        @Test
        public void getPhoneUnderStation() throws FirestationStationNotFoundException {
                String address = "validAddress";
                List<String> expectedResponse = defaultPersonsWithAddress("validAddress").stream()
                                .map(p -> p.getPhone()).collect(Collectors.toList());
                Set<Firestation> firestationsSet = new HashSet<Firestation>();
                firestationsSet.add(defaultFirestationWithAddress(address));
                when(firestationService.findAllByStation("1"))
                                .thenReturn(firestationsSet);
                when(personService.getAllPersons())
                                .thenReturn(defaultPersonsWithAddress(address));
                List<String> response = urlService.getPhoneUnderStation("1");
                assertEquals(expectedResponse, response);
        }

        @Test
        public void getPhoneUnderStationEmpty() throws FirestationStationNotFoundException {
                Set<Firestation> firestationsSet = new HashSet<Firestation>();
                firestationsSet.add(defaultFirestationWithAddress("validAddress"));
                when(firestationService.findAllByStation("1"))
                                .thenReturn(firestationsSet);
                when(personService.getAllPersons())
                                .thenReturn(defaultPersonsWithAddress("invalidAddress"));
                List<String> response = urlService.getPhoneUnderStation("1");
                assertEquals(0, response.size());
        }

        @Test
        public void getFireInformation() throws FirestationAddressNotFoundException {
                String address = "validAddress";
                when(personService.getAllPersonsWithAddress(address))
                                .thenReturn(defaultPersonsWithAddress(address));
                when(firestationService.getFirestationWithAddress(address))
                                .thenReturn(defaultFirestationWithAddress(address));
                FireInformationDTO expectedResponse = FireInformationDTO.builder()
                                .persons(defaultPersonsWithAddress(address)).station("1").build();
                FireInformationDTO response = urlService.getFireInformation(address);
                assertEquals(expectedResponse.getStation(), response.getStation());
                assertEquals(expectedResponse.getPersons(), response.getPersons());

        }
}
