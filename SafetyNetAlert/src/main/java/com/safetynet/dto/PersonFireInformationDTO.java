package com.safetynet.dto;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonFireInformationDTO {
    String name;
    String phone;
    int age;
    Set<String> medication;
    Set<String> allergies;
}
