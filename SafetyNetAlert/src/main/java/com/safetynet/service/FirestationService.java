package com.safetynet.service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.hibernate.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.safetynet.model.BadRequestException;
import com.safetynet.model.Firestation;
import com.safetynet.repository.FirestationRepository;

@Service
public class FirestationService {

    private final FirestationRepository firestationRepository;

    public FirestationService(FirestationRepository firestationRepository) {
        this.firestationRepository = firestationRepository;
    }

    public void saveFirestation(Firestation firestation) {
        Optional<Firestation> existingFirestationAtAddress = firestationRepository
                .findByAddress(firestation.getAddress());
        if (existingFirestationAtAddress.isPresent()) {
            String errorMessage = ("Already a firestation with address " + firestation.getAddress());
            System.out.println(errorMessage);
            throw new DataIntegrityViolationException(errorMessage);
        }
        firestationRepository.save(firestation);
        System.out.println("Firestation saved");
    }

    public Set<Firestation> getAllFirestations() {
        return firestationRepository.getAll();
    }

    public Firestation updateFirestation(Firestation firestation) {
        Optional<Firestation> firestationToUpdate = firestationRepository.findByAddress(firestation.getAddress());
        if (firestationToUpdate.isPresent()) {
            Firestation _firestationToUpdate = firestationToUpdate.get();
            firestationRepository.remove(_firestationToUpdate);
            firestationRepository.save(firestation);
            System.out.println("Firestation updated");
            return firestation;
        }
        throw new ObjectNotFoundException(
                "No firestation with address: " + firestation.getAddress() + " found.",
                firestationToUpdate);
    }

    public void removeFirestation(Firestation firestation) {
        if (Objects.nonNull(firestation.getAddress())) {
            System.out.println("Searching firestation with address : " + firestation.getAddress());
            Optional<Firestation> firestationToRemove = firestationRepository.findByAddress(firestation.getAddress());
            if (firestationToRemove.isPresent()) {
                firestationRepository.remove(firestationToRemove.get());
                return;
            }
            String errorMessage = "No firestation with address: " + firestation.getAddress() + " found";
            System.out.println(errorMessage);
            throw new ResourceNotFoundException(errorMessage);
        } else if (Objects.nonNull(firestation.getStation())) {
            System.out.println("Searching all firestation with station : " + firestation.getStation());
            Set<Firestation> firestationToRemove = firestationRepository.findAllByStation(firestation.getStation());
            if (firestationToRemove.size() > 0) {
                firestationRepository.removeAll(firestationToRemove);
                return;
            }
            String errorMessage = "No firestation with station: " + firestation.getStation() + " found";
            System.out.println(errorMessage);
            throw new ResourceNotFoundException(errorMessage);
        }
        String errorMessage = "Ensure the body contain either an address or a station.";
        System.out.println(errorMessage);
        throw new BadRequestException(errorMessage);
    }
}
