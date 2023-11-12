package com.safetynet.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.safetynet.model.Person;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonsInCoverageSummaryDTO {
    Set<PersonInCoverageDTO> personsInCoverage;
    int adultCount;
    int childrenCount;

    public static PersonsInCoverageSummaryDTOBuilder builder() {
        return new PersonsInCoverageSummaryDTOBuilder();
    }

    public static class PersonsInCoverageSummaryDTOBuilder {
        Set<PersonInCoverageDTO> personsInCoverage;

        public PersonsInCoverageSummaryDTOBuilder personsInCoverage(Set<Person> persons) {
            this.personsInCoverage = persons.stream()
                    .map(p -> PersonInCoverageDTO.builder()
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

@Data
@Builder
class PersonInCoverageDTO {
    String firstName;
    String lastName;
    String address;
    String phone;
}