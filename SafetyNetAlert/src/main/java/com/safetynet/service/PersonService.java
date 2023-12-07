package com.safetynet.service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.safetynet.exception.PersonAlreadyExistException;
import com.safetynet.exception.PersonBadRequestException;
import com.safetynet.exception.PersonNotFoundException;
import com.safetynet.model.Person;
import com.safetynet.repository.PersonRepository;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    Logger logger = LoggerFactory.getLogger(PersonService.class);

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void savePerson(Person person) throws PersonAlreadyExistException {
        Optional<Person> existingPerson = personRepository
                .findByName(person.getFirstName(), person.getLastName());
        if (existingPerson.isPresent()) {
            throw new PersonAlreadyExistException(person);
        }
        personRepository.save(person);
        logger.debug("Person saved : " + person.toString());
    }

    public Set<Person> getAllPersons() {
        Set<Person> persons = personRepository.getAll();
        logger.debug("Persons found : " + persons.toString());
        return persons;
    }

    public Person updatePerson(Person person) throws PersonNotFoundException {
        Optional<Person> personToUpdate = personRepository.findByName(person.getFirstName(), person.getLastName());
        if (personToUpdate.isPresent()) {
            logger.debug("Person found : " + personToUpdate.get().toString());
            Person _personToUpdate = personToUpdate.get();
            personRepository.remove(_personToUpdate);
            personRepository.save(person);
            logger.debug("Person updated : " + person.toString());
            return _personToUpdate;
        }
        throw new PersonNotFoundException(person);
    }

    public void removePerson(Person person) throws PersonNotFoundException, PersonBadRequestException {
        if (Objects.nonNull(person.getFirstName()) && Objects.nonNull(person.getLastName())) {
            Optional<Person> personToRemove = personRepository.findByName(person.getFirstName(), person.getLastName());
            if (personToRemove.isPresent()) {
                logger.debug("Person found : " + personToRemove.get().toString());
                personRepository.remove(personToRemove.get());
                return;
            }
            throw new PersonNotFoundException(person);
        }
        throw new PersonBadRequestException();
    }

    public Set<Person> getAllPersonsWithAddress(String address) {
        Set<Person> persons = getAllPersons();
        return persons.stream().filter((Person p) -> p.getAddress().equals(address)).collect(Collectors.toSet());
    }

    public Set<Person> getPersonsByName(String lastName) {
        return personRepository.findByLastName(lastName);
    }

    public Set<Person> getPersonsByCity(String city) {
        return personRepository.findByCity(city);
    }

}
