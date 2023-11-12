package com.safetynet.dto;

import java.util.List;

import com.safetynet.model.Person;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChildrenWithAddressDTO {
    String firstName;
    String lastName;
    int age;
    List<Person> family;
}
