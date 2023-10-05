package com.safetynet.repository;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.safetynet.model.Firestation;

@Repository
public class FirestationRepository {

    private Set<Firestation> firestations = new HashSet<Firestation>();

    public Set<Firestation> getAll() {
        return firestations;
    }

    public boolean save(Firestation firestation) {
        return this.firestations.add(firestation);
    }

    public Optional<Firestation> findByAddress(String address) {
        return firestations.stream()
                .filter(f -> Objects.nonNull(address) && f.getAddress().equals(address))
                .findFirst();
    }

    public Set<Firestation> findAllByStation(String station) {
        return firestations.stream()
                .filter(f -> Objects.nonNull(station) && f.getStation().equals(station))
                .collect(Collectors.toSet());
    }

    public boolean remove(Firestation firestation) {
        return this.firestations.remove(firestation);
    }

    public boolean removeAll(Set<Firestation> firestationsToRemove) {
        return this.firestations.removeAll(firestationsToRemove);
    }
}
