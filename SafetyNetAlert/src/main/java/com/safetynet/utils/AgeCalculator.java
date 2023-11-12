package com.safetynet.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

@Service
public class AgeCalculator {

    final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public int calculateAge(String birthDate) {
        LocalDate dateOfBirth = LocalDate.parse(birthDate, formatter);
        LocalDate currentDate = LocalDate.now();
        int age = currentDate.getYear() - dateOfBirth.getYear();
        if (dateOfBirth.getMonthValue() > currentDate.getMonthValue() ||
                (dateOfBirth.getMonthValue() == currentDate.getMonthValue() &&
                        dateOfBirth.getDayOfMonth() > currentDate.getDayOfMonth())) {
            age--;
        }
        return age;
    }
}
