package com.safetynet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.model.Person;
import com.safetynet.service.PersonService;

@RestController
public class PersonController {

    @Autowired
    PersonService personService;

    @PostMapping("/person")
    @ResponseStatus(HttpStatus.CREATED)
    public Person createPerson(@RequestBody Person person) {
        personService.savePerson(person);
        return person;
    }

    @PutMapping("/person")
    @ResponseStatus(HttpStatus.OK)
    public Person updatePerson(@RequestBody Person person) {
        return personService.updatePerson(person);
    }

    @DeleteMapping("/person")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@RequestBody String firstName, @RequestBody String lastName) {
        personService.removePerson(firstName, lastName);
    }

}
