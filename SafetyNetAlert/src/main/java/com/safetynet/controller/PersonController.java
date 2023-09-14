package com.safetynet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.service.PersonRepository;

@RestController
public class PersonController {

    @Autowired
    PersonRepository personRepository;
}
