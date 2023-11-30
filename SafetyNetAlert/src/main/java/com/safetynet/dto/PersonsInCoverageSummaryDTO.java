package com.safetynet.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.safetynet.model.Person;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonsInCoverageSummaryDTO {
    Set<PersonDTO> personsInCoverage;
    String address;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    int adultCount;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
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