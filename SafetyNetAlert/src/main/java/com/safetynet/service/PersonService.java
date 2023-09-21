package com.safetynet.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.safetynet.model.Person;
import com.safetynet.repository.PersonRepository;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Set<Person> getAllPerson() {
        return personRepository.getAll();
    }

}
