package com.safetynet.dto;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FireInformationDTO {
    Set<PersonFireInformationDTO> persons;
    String station;
}