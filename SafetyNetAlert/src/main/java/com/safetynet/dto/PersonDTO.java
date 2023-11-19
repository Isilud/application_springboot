package com.safetynet.dto;

import java.util.Set;

import com.safetynet.model.Person;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonDTO {
    String firstName;
    String lastName;
    String address;
    String phone;
    String email;
    int age;
    Set<Person> family;
    Set<String> medication;
    Set<String> allergies;
}
