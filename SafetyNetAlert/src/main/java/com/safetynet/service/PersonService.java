package com.safetynet.service;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
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
        Optional<Person> existingPerson = personRepository
                .findByName(person.getFirstName(), person.getLastName());
        if (existingPerson.isPresent()) {
            String errorMessage = ("Already a person called " + person.getFirstName() + " " + person.getLastName());
            System.out.println(errorMessage);
            throw new DataIntegrityViolationException(errorMessage);
        }
        personRepository.save(person);
        System.out.println("Person saved");
    }

    public Person updatePerson(Person person) {
        Optional<Person> personToUpdate = personRepository.findByName(person.getFirstName(), person.getLastName());
        if (personToUpdate.isPresent()) {
            Person _personToUpdate = personToUpdate.get();
            personRepository.remove(_personToUpdate);
            personRepository.save(person);
            System.out.println("Person updated");
            return _personToUpdate;
        }
        String errorMessage = "No person with name " + person.getFirstName() + " " + person.getLastName() + " found";
        System.out.println(errorMessage);
        throw new ResourceNotFoundException(errorMessage);
    }

    public void removePerson(String firstName, String lastName) {
        Optional<Person> personToRemove = personRepository.findByName(firstName, lastName);
        if (personToRemove.isPresent()) {
            personRepository.remove(personToRemove.get());
        }
        String errorMessage = "No person with name: " + firstName + " " + lastName + " found";
        System.out.println(errorMessage);
        throw new ResourceNotFoundException(errorMessage);
    }

}
