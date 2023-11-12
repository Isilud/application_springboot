package com.safetynet.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.safetynet.model.Person;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FireInformationDTO {
    Set<PersonFireInformationDTO> persons;
    String station;

    public static FireInformationDTOBuilder builder() {
        return new FireInformationDTOBuilder();
    }

    public static class FireInformationDTOBuilder {
        Set<PersonFireInformationDTO> persons;

        public FireInformationDTOBuilder persons(Set<Person> persons) {
            this.persons = persons.stream()
                    .map((Person p) -> PersonFireInformationDTO.builder()
                            .name(p.getFirstName())
                            .build())
                    .collect(Collectors.toSet());
            return this;
        }
    }
}

@Data
@Builder
class PersonFireInformationDTO {
    String name;
    String phone;
    int age;
    Set<String> medication;
    Set<String> allergies;
}