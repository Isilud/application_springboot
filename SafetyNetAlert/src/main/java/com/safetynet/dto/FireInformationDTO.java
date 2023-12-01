package com.safetynet.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FireInformationDTO {
    Set<PersonDTO> persons;
    String station;
}