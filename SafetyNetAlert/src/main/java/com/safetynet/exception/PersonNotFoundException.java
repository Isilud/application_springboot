package com.safetynet.exception;

import com.safetynet.model.Person;

public class PersonNotFoundException extends Exception {

    public PersonNotFoundException(Person person) {
        super("Person called " + person.getFirstName() + " " + person.getLastName()
                + " not found.");
    }

}