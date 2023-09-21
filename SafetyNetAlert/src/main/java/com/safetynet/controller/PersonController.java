package com.safetynet.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.model.Person;
import com.safetynet.service.PersonService;

@RestController
public class PersonController {

    @Autowired
    PersonService personService;

    @GetMapping("/toto")
    public ResponseEntity<Set<Person>> getAllPerson() {
        System.out.println("toto");
        return ResponseEntity.ok().body(personService.getAllPerson());
    }

}
