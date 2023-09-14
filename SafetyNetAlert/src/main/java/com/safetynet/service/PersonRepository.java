package com.safetynet.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.safetynet.model.Person;

@Service
public class PersonRepository {

    private static Set<Person> Persons = new HashSet<Person>();

    public Set<Person> getAll() {
        return Persons;
    }

    public void save(Person person) {
        PersonRepository.Persons.add(person);
    }

    public void remove(Person person) {
        PersonRepository.Persons.remove(person);
    }
}
