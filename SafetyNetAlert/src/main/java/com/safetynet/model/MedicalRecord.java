package com.safetynet.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MedicalRecord {
    String firstName;
    String lastName;
    String birthDate;
    List<String> medication;
    List<String> allergies;
}
