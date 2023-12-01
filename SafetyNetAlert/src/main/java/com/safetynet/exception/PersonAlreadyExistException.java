package com.safetynet.exception;

import com.safetynet.model.Person;

public class PersonAlreadyExistException extends Exception {

    public PersonAlreadyExistException(Person person) {
        super("Person called " + person.getFirstName() + " " + person.getLastName()
                + " already exist.");
    }

}
