package com.safetynet.service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.controller.PersonController;
import com.safetynet.model.Firestation;
import com.safetynet.model.MedicalRecord;
import com.safetynet.model.Person;

import jakarta.annotation.PostConstruct;

@Service("JsonDataService")
public class JsonDataService implements DataService {

    @Autowired
    PersonRepository personRepository;
    @Autowired
    FirestationRepository firestationRepository;
    @Autowired
    MedicalRecordRepository medicalRecordRepository;

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