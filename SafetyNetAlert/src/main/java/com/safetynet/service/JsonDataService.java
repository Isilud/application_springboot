package com.safetynet.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.model.Firestation;
import com.safetynet.model.MedicalRecord;
import com.safetynet.model.Person;
import com.safetynet.repository.FirestationRepository;
import com.safetynet.repository.MedicalRecordRepository;
import com.safetynet.repository.PersonRepository;

import jakarta.annotation.PostConstruct;

@Service
public class JsonDataService implements DataService {

    private final PersonRepository personRepository;

    private final FirestationRepository firestationRepository;

    private final MedicalRecordRepository medicalRecordRepository;

    public JsonDataService(PersonRepository personRepository, FirestationRepository firestationRepository,
            MedicalRecordRepository medicalRecordRepository) {
        this.personRepository = personRepository;
        this.firestationRepository = firestationRepository;
        this.medicalRecordRepository = medicalRecordRepository;
    }

    @PostConstruct
    public void setUp() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        var jsonFile = new File("src/main/resources/data.json");
        String jsonContent = new String(Files.readAllBytes(jsonFile.toPath()));
        JsonNode jsonNode = objectMapper.readTree(jsonContent);
        JsonNode personsNode = jsonNode.get("persons");
        JsonNode firestationsNode = jsonNode.get("firestations");
        JsonNode medicalrecordsNode = jsonNode.get("medicalrecords");
        for (JsonNode personNode : personsNode) {
            Person person = Person.builder()
                    .firstName(personNode.path("firstName").asText())
                    .lastName(personNode.path("lastName").asText())
                    .address(personNode.path("address").asText())
                    .city(personNode.path("city").asText())
                    .zip(personNode.path("zip").asText())
                    .phone(personNode.path("phone").asText())
                    .email(personNode.path("email").asText())
                    .build();
            personRepository.save(person);
        }
        ;
        for (JsonNode firestationNode : firestationsNode) {
            Firestation firestation = Firestation.builder()
                    .address(firestationNode.path("address").asText())
                    .station(firestationNode.path("station").asText())
                    .build();
            firestationRepository.save(firestation);
        }
        for (JsonNode medicalRecordNode : medicalrecordsNode) {
            Set<String> medications = new HashSet<String>();
            JsonNode medicationsArrayNode = medicalRecordNode.get("medications");
            for (final JsonNode medicationNode : medicationsArrayNode) {
                medications.add(medicationNode.asText());
            }
            Set<String> allergies = new HashSet<String>();
            JsonNode allergiesArrayNode = medicalRecordNode.get("allergies");
            for (final JsonNode allergieNode : allergiesArrayNode) {
                allergies.add(allergieNode.asText());
            }
            MedicalRecord medicalRecord = MedicalRecord.builder()
                    .firstName(medicalRecordNode.path("firstName").asText())
                    .lastName(medicalRecordNode.path("lastName").asText())
                    .birthDate(medicalRecordNode.path("birthDate").asText())
                    .medication(medications)
                    .allergies(allergies)
                    .build();
            medicalRecordRepository.save(medicalRecord);
        }
        ;

        System.out.println(personRepository.getAll());
        System.out.println(firestationRepository.getAll());
        System.out.println(medicalRecordRepository.getAll());
    }

}