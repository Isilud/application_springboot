package com.safetynet.service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.safetynet.exception.FirestationAlreadyExistException;
import com.safetynet.exception.FirestationBadRequestException;
import com.safetynet.exception.FirestationStationNotFoundException;
import com.safetynet.exception.FirestationAddressNotFoundException;
import com.safetynet.model.Firestation;
import com.safetynet.repository.FirestationRepository;

@Service
public class FirestationService {

    private final FirestationRepository firestationRepository;

    Logger logger = LoggerFactory.getLogger(FirestationService.class);

    public FirestationService(FirestationRepository firestationRepository) {
        this.firestationRepository = firestationRepository;
    }

    public void saveFirestation(Firestation firestation) throws FirestationAlreadyExistException {
        Optional<Firestation> existingFirestationAtAddress = firestationRepository
                .findByAddress(firestation.getAddress());
        if (existingFirestationAtAddress.isPresent()) {
            throw new FirestationAlreadyExistException(firestation);
        }
        firestationRepository.save(firestation);
        logger.info("Firestation saved : " + firestation.toString());
    }

    public Set<Firestation> getAllFirestations() {
        Set<Firestation> firestations = firestationRepository.getAll();
        logger.info("Firestations found : " + firestations.toString());
        return firestations;
    }

    public Firestation updateFirestation(Firestation firestation) throws FirestationAddressNotFoundException {
        Optional<Firestation> firestationToUpdate = firestationRepository.findByAddress(firestation.getAddress());
        if (firestationToUpdate.isPresent()) {
            logger.info("Firestation found : " + firestationToUpdate.get().toString());
            Firestation _firestationToUpdate = firestationToUpdate.get();
            firestationRepository.remove(_firestationToUpdate);
            firestationRepository.save(firestation);
            logger.info("Firestation updated with : " + firestation.toString());
            return firestation;
        }
        throw new FirestationAddressNotFoundException(firestation);
    }

    public void removeFirestation(Firestation firestation) throws FirestationAddressNotFoundException,
            FirestationBadRequestException, FirestationStationNotFoundException {
        if (Objects.nonNull(firestation.getAddress())) {
            Optional<Firestation> firestationToRemove = firestationRepository.findByAddress(firestation.getAddress());
            if (firestationToRemove.isPresent()) {
                logger.info("Firestation found : " + firestationToRemove.get().toString());
                firestationRepository.remove(firestationToRemove.get());
                return;
            }
            throw new FirestationAddressNotFoundException(firestation);
        } else if (Objects.nonNull(firestation.getStation())) {
            Set<Firestation> firestationToRemove = firestationRepository.findAllByStation(firestation.getStation());
            if (firestationToRemove.size() > 0) {
                logger.info("Firestations found : " + firestationToRemove.toString());
                firestationRepository.removeAll(firestationToRemove);
                return;
            }
            throw new FirestationStationNotFoundException(firestation);
        }
        throw new FirestationBadRequestException();
    }
}
