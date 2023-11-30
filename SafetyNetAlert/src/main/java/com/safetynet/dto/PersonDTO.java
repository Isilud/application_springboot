package com.safetynet.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.safetynet.model.Person;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonDTO {
    String firstName;
    String lastName;
    String address;
    String phone;
    String email;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    int age;
    Set<Person> family;
    Set<String> medication;
    Set<String> allergies;
}
