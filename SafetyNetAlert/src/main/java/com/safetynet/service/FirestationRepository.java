package com.safetynet.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.safetynet.model.Firestation;

@Service
public class FirestationRepository {

    private static Set<Firestation> Firestations = new HashSet<Firestation>();

    public Set<Firestation> getAll() {
        return Firestations;
    }

    public void save(Firestation firestation) {
        FirestationRepository.Firestations.add(firestation);
    }

    public void remove(Firestation firestation) {
        FirestationRepository.Firestations.remove(firestation);
    }
}
