package com.safetynet.controller;

import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.model.Firestation;
import com.safetynet.service.FirestationService;

@RestController
public class FirestationController {

    @Autowired
    FirestationService firestationService;

    @PostMapping("/firestation")
    public ResponseEntity<Firestation> createFirestation(@RequestBody Firestation firestation) {
        try {
            firestationService.saveFirestation(firestation);
            return new ResponseEntity<>(firestation, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/firestation")
    public ResponseEntity<Set<Firestation>> getAllFirestations() {
        try {
            Set<Firestation> firestations = firestationService.getAllFirestations();
            return new ResponseEntity<>(firestations, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/firestation")
    public ResponseEntity<Firestation> updateFirestation(@RequestBody Firestation firestation) {
        try {
            Firestation updatedFirestation = firestationService.updateFirestation(firestation);
            return new ResponseEntity<>(updatedFirestation, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/firestation")
    public ResponseEntity<Void> deleteFirestation(@RequestBody Firestation firestation) {
        try {
            if (Objects.nonNull(firestation.getAddress())) {
                boolean isRemoved = firestationService.removeFirestationWithAddress(firestation.getAddress());
                if (isRemoved)
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                boolean isRemoved = firestationService.removeFirestationWithStation(firestation.getStation());
                if (isRemoved)
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
