package com.safetynet.repository;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Repository;
import com.safetynet.model.Person;

@Repository
public class PersonRepository {

    private Set<Person> persons = new HashSet<Person>();

    public Set<Person> getAll() {
        return persons;
    }

    public void save(Person person) {
        persons.add(person);
    }

    public void remove(Person person) {
        persons.remove(person);
    }
}
