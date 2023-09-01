package com.safetynet.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Person {
    String firstName;
    String lastName;
    String address;
    String city;
    String zip;
    String phone;
    String email;
}
