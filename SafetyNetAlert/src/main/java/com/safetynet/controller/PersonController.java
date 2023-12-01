package com.safetynet.controller;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.exception.PersonAlreadyExistException;
import com.safetynet.exception.PersonBadRequestException;
import com.safetynet.exception.PersonNotFoundException;
import com.safetynet.model.Person;
import com.safetynet.service.PersonService;

@RestController
public class PersonController {

    @Autowired
    PersonService personService;

    Logger logger = LoggerFactory.getLogger(PersonController.class);

    @PostMapping("/person")
    @ResponseStatus(HttpStatus.CREATED)
    public Person createPerson(@RequestBody Person person) throws PersonAlreadyExistException {
        logger.info("Creating new person : " + person.toString());
        personService.savePerson(person);
        return person;
    }

    @GetMapping("/person")
    @ResponseStatus(HttpStatus.OK)
    public Set<Person> getAllPersons() {
        logger.info("Fetching all persons");
        return personService.getAllPersons();
    }

    @PutMapping("/person")
    @ResponseStatus(HttpStatus.OK)
    public Person updatePerson(@RequestBody Person person) throws PersonNotFoundException {
        logger.info("Updating person : " + person.toString());
        return personService.updatePerson(person);
    }

    @DeleteMapping("/person")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@RequestBody Person person)
            throws PersonNotFoundException, PersonBadRequestException {
        logger.info("Deleting person : " + person.toString());
        personService.removePerson(person);
        return;
    }

}
