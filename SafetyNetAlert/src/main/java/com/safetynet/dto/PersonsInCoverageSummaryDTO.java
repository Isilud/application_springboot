package com.safetynet.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.safetynet.model.Person;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonsInCoverageSummaryDTO {
    Set<PersonDTO> personsInCoverage;
    String address;
    int adultCount;
    int childrenCount;

    public static PersonsInCoverageSummaryDTOBuilder builder() {
        return new PersonsInCoverageSummaryDTOBuilder();
    }

    public static class PersonsInCoverageSummaryDTOBuilder {
        Set<PersonDTO> personsInCoverage;

        public PersonsInCoverageSummaryDTOBuilder personsInCoverage(Set<Person> persons) {
            this.personsInCoverage = persons.stream()
                    .map(p -> PersonDTO.builder()
                            .firstName(p.getFirstName())
                            .lastName(p.getLastName())
                            .address(p.getAddress())
                            .phone(p.getPhone())
                            .build())
                    .collect(Collectors.toSet());
            return this;
        }
    }
}