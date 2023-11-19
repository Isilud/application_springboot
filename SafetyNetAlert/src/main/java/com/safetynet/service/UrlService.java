package com.safetynet.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.safetynet.dto.FireInformationDTO;
import com.safetynet.dto.PersonDTO;
import com.safetynet.dto.PersonsInCoverageSummaryDTO;
import com.safetynet.exception.FirestationAddressNotFoundException;
import com.safetynet.exception.FirestationStationNotFoundException;
import com.safetynet.exception.MedicalRecordNotFoundException;
import com.safetynet.model.Firestation;
import com.safetynet.model.MedicalRecord;
import com.safetynet.model.Person;
import com.safetynet.utils.AgeCalculator;

@Service
public class UrlService {

    private final MedicalRecordService medicalRecordService;
    private final PersonService personService;
    private final FirestationService firestationService;

    Logger logger = LoggerFactory.getLogger(MedicalRecordService.class);

    public UrlService(MedicalRecordService medicalRecordService, PersonService personService,
            FirestationService firestationService) {
        this.medicalRecordService = medicalRecordService;
        this.personService = personService;
        this.firestationService = firestationService;
    }

    public PersonsInCoverageSummaryDTO getPeopleWithStationCoverage(String stationNumber)
            throws FirestationStationNotFoundException, MedicalRecordNotFoundException {
        AgeCalculator calculator = new AgeCalculator();
        Set<Firestation> firestations = firestationService.findAllByStation(stationNumber);
        logger.info("Firestation found : " + firestations);
        Set<Person> persons = personService.getAllPersons();
        logger.info("Persons covered : " + persons);
        Set<Person> coveredPeoples = new HashSet<>();
        int adultCount = 0;
        int childCount = 0;
        for (Person person : persons) {
            for (Firestation firestation : firestations) {
                if (person.getAddress().equals(firestation.getAddress())) {
                    String birthdate = medicalRecordService.getRecord(person.getFirstName(), person.getLastName())
                            .getBirthdate();
                    if (calculator.calculateAge(birthdate) > 18) {
                        adultCount += 1;
                    } else {
                        childCount += 1;
                    }
                    coveredPeoples.add(person);
                    break;
                }
            }
        }
        logger.info("Adult/Children counted : " + adultCount + "/" + childCount);
        return PersonsInCoverageSummaryDTO.builder()
                .personsInCoverage(coveredPeoples)
                .adultCount(adultCount)
                .childrenCount(childCount)
                .build();
    }

    public Set<PersonDTO> getChildrenAtAddress(String address) throws MedicalRecordNotFoundException {
        AgeCalculator calculator = new AgeCalculator();
        Set<Person> persons = personService.getAllPersonsWithAddress(address);
        Set<PersonDTO> childrenList = new HashSet<PersonDTO>();
        for (Person person : persons) {
            String birthdate = medicalRecordService.getRecord(person.getFirstName(), person.getLastName())
                    .getBirthdate();
            int age = calculator.calculateAge(birthdate);
            if (age <= 18) {
                childrenList.add(PersonDTO.builder()
                        .firstName(person.getFirstName())
                        .lastName(person.getLastName())
                        .age(age)
                        .family(persons.stream()
                                .filter((Person f) -> !f.getLastName().equals(person.getLastName())
                                        || !f.getFirstName().equals(person.getFirstName()))
                                .collect(Collectors.toSet()))
                        .build());
            }
        }
        return childrenList;
    }

    public List<String> getPhoneUnderStation(String stationNumber) throws FirestationStationNotFoundException {
        List<String> phoneList = new ArrayList<>();
        Set<Firestation> firestations = firestationService.findAllByStation(stationNumber);
        logger.info("Firestation found : " + firestations);
        Set<Person> persons = personService.getAllPersons();
        logger.info("Persons covered : " + persons);
        for (Person person : persons) {
            for (Firestation firestation : firestations) {
                if ((person.getAddress().equals(firestation.getAddress())))
                    phoneList.add(person.getPhone());
            }
        }
        return phoneList;
    }

    public FireInformationDTO getFireInformation(String address)
            throws FirestationAddressNotFoundException, MedicalRecordNotFoundException {
        AgeCalculator calculator = new AgeCalculator();
        Firestation firestation = firestationService.getFirestationWithAddress(address);
        Set<Person> persons = personService.getAllPersonsWithAddress(address);
        Set<PersonDTO> personFireInfos = new HashSet<PersonDTO>();
        for (Person person : persons) {
            MedicalRecord record = medicalRecordService.getRecord(person.getFirstName(), person.getLastName());
            personFireInfos.add(PersonDTO.builder()
                    .age(calculator.calculateAge(record.getBirthdate()))
                    .allergies(record.getAllergies())
                    .medication(record.getMedication())
                    .phone(person.getPhone())
                    .firstName(person.getFirstName())
                    .lastName(person.getLastName())
                    .build());
        }
        FireInformationDTO information = FireInformationDTO.builder()
                .persons(personFireInfos)
                .station(firestation.getStation())
                .build();
        return information;
    }

}
