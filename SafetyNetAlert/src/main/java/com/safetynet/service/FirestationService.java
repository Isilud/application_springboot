package com.safetynet.service;

import java.util.Optional;
import java.util.Set;

import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import com.safetynet.model.Firestation;
import com.safetynet.repository.FirestationRepository;

@Service
public class FirestationService {

    private final FirestationRepository firestationRepository;

    public FirestationService(FirestationRepository firestationRepository) {
        this.firestationRepository = firestationRepository;
    }

    public void saveFirestation(Firestation firestation) {
        firestationRepository.save(firestation);
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
            return firestation;
        }
        throw new ObjectNotFoundException(
                "No firestation with address: " + firestation.getAddress() + " found.",
                firestationToUpdate);
    }

    public boolean removeFirestationWithAddress(String address) {
        Optional<Firestation> firestationToRemove = firestationRepository.findByAddress(address);
        if (firestationToRemove.isPresent()) {
            return firestationRepository.remove(firestationToRemove.get());
        } else
            return false;
    }

    public boolean removeFirestationWithStation(String station) {
        Set<Firestation> firestationToRemove = firestationRepository.findAllByStation(station);
        if (firestationToRemove.size() == 0) {
            return false;
        } else {
            return firestationRepository.removeAll(firestationToRemove);
        }
    }

}
