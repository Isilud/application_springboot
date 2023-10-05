package com.safetynet.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.model.Firestation;
import com.safetynet.service.FirestationService;

@RestController
public class FirestationController {

    @Autowired
    FirestationService firestationService;

    @PostMapping("/firestation")
    @ResponseStatus(HttpStatus.CREATED)
    public Firestation createFirestation(@RequestBody Firestation firestation) {
        System.out.println("Creating new firestation");
        firestationService.saveFirestation(firestation);
        return firestation;
    }

    @GetMapping("/firestation")
    @ResponseStatus(HttpStatus.OK)
    public Set<Firestation> getAllFirestations() {
        System.out.println("Fetching all firestations");
        return firestationService.getAllFirestations();
    }

    @PutMapping("/firestation")
    @ResponseStatus(HttpStatus.OK)
    public Firestation updateFirestation(@RequestBody Firestation firestation) {
        System.out.println("Updating firestation");
        return firestationService.updateFirestation(firestation);
    }

    @DeleteMapping("/firestation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFirestation(@RequestBody Firestation firestation) {
        System.out.println("Deleting firestation");
        firestationService.removeFirestation(firestation);
        return;
    }
}
