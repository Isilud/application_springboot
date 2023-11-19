package com.safetynet.SafetyNetAlert.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.dto.FireInformationDTO;
import com.safetynet.dto.PersonDTO;
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

        // Service
        private UrlService urlService;

        // Persons
        Person JohnDoe;
        Person JaneDoe;
        Person JosephDoe;

        // MedicalRecords
        MedicalRecord JohnDoeRecord;
        MedicalRecord JaneDoeRecord;
        MedicalRecord JosephDoeRecord;

        // Firestations
        Firestation firstFirestation;
        Firestation secondFirestation;

        @BeforeEach
        public void clearServices() {
                urlService = new UrlService(medicalRecordService, personService, firestationService);
        }

        @BeforeEach
        public void setPersons() {
                JohnDoe = Person.builder().firstName("John").lastName("Doe").address("anAddress").city("city")
                                .zip("1234").phone("0123456").email("JohnDoe").build();
                JaneDoe = Person.builder().firstName("Jane").lastName("Doe").address("anAddress").city("city")
                                .zip("1234").phone("7891011").email("JaneDoe").build();
                JosephDoe = Person.builder().firstName("Joseph").lastName("Doe").address("anAddress").city("city")
                                .zip("1234").phone("12131415").email("None").build();
        }

        @BeforeEach
        public void setMedicalRecord() {
                Set<String> medication = new HashSet<>();
                medication.add("doliprane");
                Set<String> allergies = new HashSet<>();
                allergies.add("lactose");
                JohnDoeRecord = MedicalRecord.builder()
                                .firstName("John")
                                .lastName("Doe")
                                .birthdate("01/02/1994")
                                .allergies(allergies)
                                .medication(medication)
                                .build();
                JaneDoeRecord = MedicalRecord.builder()
                                .firstName("Jane")
                                .lastName("Doe")
                                .birthdate("03/04/1995")
                                .allergies(new HashSet<>())
                                .medication(new HashSet<>())
                                .build();
                JosephDoeRecord = MedicalRecord.builder()
                                .firstName("Joseph")
                                .lastName("Doe")
                                .birthdate("01/02/2017")
                                .allergies(new HashSet<>())
                                .medication(new HashSet<>())
                                .build();
        }

        @BeforeEach
        public void setFirestation() {
                firstFirestation = Firestation.builder()
                                .address("anAddress")
                                .station("1")
                                .build();
                secondFirestation = Firestation.builder()
                                .address("anotherAddress")
                                .station("2")
                                .build();
        }

        @Test
        public void getPeopleWithStationCoverage()
                        throws FirestationStationNotFoundException, MedicalRecordNotFoundException {
                // Given
                Set<Person> persons = new HashSet<Person>();
                persons.add(JohnDoe);
                JaneDoe.setAddress("anotherAddress");
                persons.add(JaneDoe);
                persons.add(JosephDoe);
                Set<Firestation> firestations = new HashSet<Firestation>();
                firestations.add(firstFirestation);
                when(firestationService.findAllByStation("1"))
                                .thenReturn(firestations);
                when(personService.getAllPersons())
                                .thenReturn(persons);
                when(medicalRecordService.getRecord("John", "Doe"))
                                .thenReturn(JohnDoeRecord);
                when(medicalRecordService.getRecord("Joseph", "Doe"))
                                .thenReturn(JosephDoeRecord);
                // When
                PersonsInCoverageSummaryDTO response = urlService.getPeopleWithStationCoverage("1");
                // Then
                assertFalse(response.getPersonsInCoverage().stream().anyMatch(p -> p.getFirstName().equals("Jane")));
                assertEquals(2, response.getPersonsInCoverage().size());
                assertEquals(1, response.getAdultCount());
                assertEquals(1, response.getChildrenCount());
        }

        @Test
        public void getChildrenAtAddress() throws MedicalRecordNotFoundException {
                // Given
                Set<Person> persons = new HashSet<Person>();
                persons.add(JohnDoe);
                persons.add(JaneDoe);
                persons.add(JosephDoe);
                when(personService.getAllPersonsWithAddress("anAddress"))
                                .thenReturn(persons);
                when(medicalRecordService.getRecord("John", "Doe"))
                                .thenReturn(JohnDoeRecord);
                when(medicalRecordService.getRecord("Jane", "Doe"))
                                .thenReturn(JaneDoeRecord);
                when(medicalRecordService.getRecord("Joseph", "Doe"))
                                .thenReturn(JosephDoeRecord);
                // When
                Set<PersonDTO> response = urlService.getChildrenAtAddress("anAddress");
                // Then
                assertEquals(1, response.size());
                PersonDTO child = response.stream().findFirst().get(); // Existence ensured by previous assert
                assertEquals(2, child.getFamily().size());
                List<String> firstNames = child.getFamily().stream().map(p -> p.getFirstName()).toList();
                assertFalse(firstNames.contains(child.getFirstName()));
        }

        @Test
        public void getPhoneUnderStation() throws FirestationStationNotFoundException {
                // Given
                Set<Person> persons = new HashSet<Person>();
                persons.add(JohnDoe);
                persons.add(JaneDoe);
                Set<Firestation> firestations = new HashSet<Firestation>();
                firestations.add(firstFirestation);
                when(firestationService.findAllByStation("1"))
                                .thenReturn(firestations);
                when(personService.getAllPersons())
                                .thenReturn(persons);
                // When
                List<String> response = urlService.getPhoneUnderStation("1");
                // Then
                List<String> phoneList = new ArrayList<String>();
                phoneList.add("0123456");
                phoneList.add("7891011");
                assertEquals(phoneList, response);
        }

        @Test
        public void getPhoneUnderStationEmpty() throws FirestationStationNotFoundException {
                // Given
                Set<Person> persons = new HashSet<Person>();
                JohnDoe.setAddress("invalideAddress");
                persons.add(JohnDoe);
                Set<Firestation> firestations = new HashSet<Firestation>();
                firestations.add(firstFirestation);
                when(firestationService.findAllByStation("1"))
                                .thenReturn(firestations);
                when(personService.getAllPersons())
                                .thenReturn(persons);
                // When
                List<String> response = urlService.getPhoneUnderStation("1");
                // Then
                assertEquals(0, response.size());
        }

        @Test
        public void getFireInformation() throws FirestationAddressNotFoundException, MedicalRecordNotFoundException {
                // Given
                Set<Person> persons = new HashSet<Person>();
                persons.add(JohnDoe);
                persons.add(JaneDoe);
                persons.add(JosephDoe);
                when(personService.getAllPersonsWithAddress("anAddress"))
                                .thenReturn(persons);
                when(firestationService.getFirestationWithAddress("anAddress"))
                                .thenReturn(firstFirestation);
                when(medicalRecordService.getRecord("John", "Doe"))
                                .thenReturn(JohnDoeRecord);
                when(medicalRecordService.getRecord("Jane", "Doe"))
                                .thenReturn(JaneDoeRecord);
                when(medicalRecordService.getRecord("Joseph", "Doe"))
                                .thenReturn(JosephDoeRecord);
                // When
                FireInformationDTO response = urlService.getFireInformation("anAddress");
                // Then
                assertEquals(3, response.getPersons().size());
                assertEquals("1", response.getStation());
                Optional<PersonDTO> johnInformations = response.getPersons().stream()
                                .filter(p -> p.getFirstName().equals("John")).findFirst();
                assertTrue(johnInformations.isPresent());
                Set<String> medication = new HashSet<>();
                medication.add("doliprane");
                Set<String> allergies = new HashSet<>();
                allergies.add("lactose");
                assertEquals(allergies, johnInformations.get().getAllergies());
                assertEquals(medication, johnInformations.get().getMedication());
        }

        @Test
        public void getPersonsInCoverage() throws FirestationStationNotFoundException, MedicalRecordNotFoundException {
                // Given
                Set<Person> personsAnAddress = new HashSet<Person>();
                personsAnAddress.add(JohnDoe);
                Set<Person> personsAnotherAddress = new HashSet<Person>();
                JaneDoe.setAddress("anotherAddress");
                personsAnotherAddress.add(JaneDoe);
                when(personService.getAllPersonsWithAddress("anAddress"))
                                .thenReturn(personsAnAddress);
                when(personService.getAllPersonsWithAddress("anotherAddress"))
                                .thenReturn(personsAnotherAddress);
                Set<Firestation> firstStation = new HashSet<Firestation>();
                firstStation.add(firstFirestation);
                when(firestationService.findAllByStation("1"))
                                .thenReturn(firstStation);
                Set<Firestation> secondStation = new HashSet<Firestation>();
                secondStation.add(secondFirestation);
                when(firestationService.findAllByStation("2"))
                                .thenReturn(secondStation);
                when(medicalRecordService.getRecord("John", "Doe"))
                                .thenReturn(JohnDoeRecord);
                when(medicalRecordService.getRecord("Jane", "Doe"))
                                .thenReturn(JaneDoeRecord);
                // When
                List<String> stationList = new ArrayList<String>();
                stationList.add("1");
                stationList.add("2");
                List<PersonsInCoverageSummaryDTO> response = urlService.getFloodCoverage(stationList);
                // Then
                assertEquals(2, response.size());
                Optional<PersonsInCoverageSummaryDTO> firstCoverage = response.stream()
                                .filter(p -> p.getAddress().equals("anAddress")).findFirst();
                assertTrue(firstCoverage.isPresent());
                Optional<PersonsInCoverageSummaryDTO> secondCoverage = response.stream()
                                .filter(p -> p.getAddress().equals("anotherAddress")).findFirst();
                assertTrue(secondCoverage.isPresent());
                assertEquals(1, firstCoverage.get().getPersonsInCoverage().size());
                assertEquals(1, secondCoverage.get().getPersonsInCoverage().size());
                PersonDTO johnInformation = firstCoverage.get().getPersonsInCoverage().stream().findFirst().get();
                assertEquals("John", johnInformation.getFirstName());
                Set<String> medication = new HashSet<>();
                medication.add("doliprane");
                Set<String> allergies = new HashSet<>();
                allergies.add("lactose");
                assertEquals(medication, johnInformation.getMedication());
                assertEquals(allergies, johnInformation.getAllergies());
                assertEquals("0123456", johnInformation.getPhone());
        }

        @Test
        public void getPersonInfo() {
                // Given
                Set<Person> persons = new HashSet<Person>();
                persons.add(JohnDoe);
                persons.add(JaneDoe);
                persons.add(JosephDoe);
                when(personService.getPersonsByName("Doe"))
                                .thenReturn(persons);

        }

}
