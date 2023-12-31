package com.safetynet.model;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MedicalRecord {
    String firstName;
    String lastName;
    String birthdate;
    Set<String> medication;
    Set<String> allergies;
}
