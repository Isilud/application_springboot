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

import com.safetynet.exception.FirestationAlreadyExistException;
import com.safetynet.exception.FirestationBadRequestException;
import com.safetynet.exception.FirestationStationNotFoundException;
import com.safetynet.exception.FirestationAddressNotFoundException;
import com.safetynet.model.Firestation;
import com.safetynet.service.FirestationService;

@RestController
public class FirestationController {

    @Autowired
    FirestationService firestationService;

    Logger logger = LoggerFactory.getLogger(FirestationController.class);

    @PostMapping("/firestation")
    @ResponseStatus(HttpStatus.CREATED)
    public Firestation createFirestation(@RequestBody Firestation firestation) throws FirestationAlreadyExistException {
        logger.info("Creating new firestation : " + firestation.toString());
        firestationService.saveFirestation(firestation);
        return firestation;
    }

    @GetMapping("/firestation")
    @ResponseStatus(HttpStatus.OK)
    public Set<Firestation> getAllFirestations() {
        logger.info("Fetching all firestations");
        return firestationService.getAllFirestations();
    }

    @PutMapping("/firestation")
    @ResponseStatus(HttpStatus.OK)
    public Firestation updateFirestation(@RequestBody Firestation firestation)
            throws FirestationAddressNotFoundException {
        logger.info("Updating firestation : " + firestation.toString());
        return firestationService.updateFirestation(firestation);
    }

    @DeleteMapping("/firestation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFirestation(@RequestBody Firestation firestation) throws FirestationAddressNotFoundException,
            FirestationBadRequestException, FirestationStationNotFoundException {
        logger.info("Deleting firestation : " + firestation.toString());
        firestationService.removeFirestation(firestation);
        return;
    }
}
