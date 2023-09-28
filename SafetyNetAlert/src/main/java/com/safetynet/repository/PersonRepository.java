package com.safetynet.repository;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
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

    public Optional<Person> findByName(String firstName, String lastName) {
        return persons.stream().filter(p -> Objects.nonNull(firstName) && Objects.nonNull(lastName)
                && Objects.equals(p.getFirstName(), firstName) && Objects.equals(p.getLastName(), lastName))
                .findFirst();
    }

    public void remove(Person person) {
        persons.remove(person);
    }
}
