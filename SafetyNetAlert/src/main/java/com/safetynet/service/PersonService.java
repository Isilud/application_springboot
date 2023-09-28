package com.safetynet.service;

import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import com.safetynet.model.Person;
import com.safetynet.repository.PersonRepository;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void savePerson(Person person) {
        personRepository.save(person);
    }

    public Person updatePerson(Person person) {
        Optional<Person> personToUpdate = personRepository.findByName(person.getFirstName(), person.getLastName());
        if (personToUpdate.isPresent()) {
            Person _personToUpdate = personToUpdate.get();
            _personToUpdate.setAddress(person.getAddress());
            _personToUpdate.setCity(person.getCity());
            _personToUpdate.setEmail(person.getEmail());
            _personToUpdate.setPhone(person.getPhone());
            _personToUpdate.setZip(person.getZip());
            return _personToUpdate;
        }
        throw new ObjectNotFoundException(
                "No person with name: " + person.getFirstName() + " " + person.getLastName() + " found.",
                personToUpdate);
    }

    public void removePerson(String firstName, String lastName) {
        Optional<Person> personToRemove = personRepository.findByName(firstName, lastName);
        if (personToRemove.isPresent()) {
            personRepository.remove(personToRemove.get());
        }
        throw new ObjectNotFoundException(
                "No person with name: " + firstName + " " + lastName + " found.",
                personToRemove);
    }

}
