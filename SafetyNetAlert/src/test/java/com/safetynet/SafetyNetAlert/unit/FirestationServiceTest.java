package com.safetynet.SafetyNetAlert.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.exception.FirestationAddressNotFoundException;
import com.safetynet.exception.FirestationAlreadyExistException;
import com.safetynet.exception.FirestationBadRequestException;
import com.safetynet.exception.FirestationStationNotFoundException;
import com.safetynet.model.Firestation;
import com.safetynet.repository.FirestationRepository;
import com.safetynet.service.FirestationService;

@ExtendWith(MockitoExtension.class)
public class FirestationServiceTest {

    @Mock
    private FirestationRepository firestationRepository;

    private FirestationService firestationService;

    public Firestation defaultFirestation() {
        return Firestation.builder().address("address").station("1").build();
    }

    @BeforeEach
    public void clear() {
        firestationService = new FirestationService(firestationRepository);
    }

    @Test
    public void testSaveFirestation() throws FirestationAlreadyExistException {
        when(firestationRepository.findByAddress(defaultFirestation().getAddress())).thenReturn(Optional.empty());
        firestationService.saveFirestation(defaultFirestation());
        verify(firestationRepository).save(defaultFirestation());
    }

    @Test
    public void testSaveExistingFirestation() {
        when(firestationRepository.findByAddress(defaultFirestation().getAddress())).thenReturn(Optional.of(defaultFirestation()));
        assertThrows(FirestationAlreadyExistException.class, () -> firestationService.saveFirestation(defaultFirestation()));
    }

    @Test
    public void testGetFirestations() {
        Set<Firestation> expectedList = new HashSet<Firestation>();
        expectedList.add(defaultFirestation());
        when(firestationRepository.getAll()).thenReturn(expectedList);
        Set<Firestation> registeredFirestations = firestationService.getAllFirestations();
        assertEquals(registeredFirestations, expectedList);
    }

    @Test
    public void testUpdateFirestation() throws FirestationAddressNotFoundException {
        Firestation initialFirestation = defaultFirestation();
        Firestation updatedFirestation = defaultFirestation();
        updatedFirestation.setStation("5");
        when(firestationRepository.findByAddress(initialFirestation.getAddress()))
                .thenReturn(Optional.of(initialFirestation));
        firestationService.updateFirestation(updatedFirestation);
        verify(firestationRepository).remove(initialFirestation);
        verify(firestationRepository).save(updatedFirestation);
    }

    @Test
    public void testUpdateFirestationNotFound() {
        when(firestationRepository.findByAddress(defaultFirestation().getAddress()))
                .thenReturn(Optional.empty());
        assertThrows(FirestationAddressNotFoundException.class, () -> firestationService.updateFirestation(defaultFirestation()));
    }

    @Test
    public void testDeleteFirestation() throws FirestationAddressNotFoundException, FirestationBadRequestException, FirestationStationNotFoundException {
        when(firestationRepository.findByAddress(defaultFirestation().getAddress()))
                .thenReturn(Optional.of(defaultFirestation()));
        firestationService.removeFirestation(defaultFirestation());
        verify(firestationRepository).remove(defaultFirestation());
    }

    @Test
    public void testDeleteFirestationByAddress() throws FirestationAddressNotFoundException,
            FirestationBadRequestException, FirestationStationNotFoundException {
        Firestation firestationToRemove = Firestation.builder().address(defaultFirestation().getAddress()).build();
        when(firestationRepository.findByAddress(defaultFirestation().getAddress()))
                .thenReturn(Optional.of(defaultFirestation()));
        firestationService.removeFirestation(firestationToRemove);
        verify(firestationRepository).remove(defaultFirestation());
    }

    @Test
    public void testDeleteFirestationByStation() throws FirestationAddressNotFoundException,
            FirestationBadRequestException, FirestationStationNotFoundException {
        Firestation firestationToRemove = Firestation.builder().station(defaultFirestation().getStation()).build();
        Set<Firestation> expectedList = new HashSet<Firestation>();
        expectedList.add(defaultFirestation());
        when(firestationRepository.findAllByStation(defaultFirestation().getStation()))
                .thenReturn(expectedList);
        firestationService.removeFirestation(firestationToRemove);
        verify(firestationRepository).removeAll(expectedList);
    }

    @Test
    public void testDeleteFirestationAddressNotFound() {
        Firestation firestationToRemove = Firestation.builder().address(defaultFirestation().getAddress()).build();
        when(firestationRepository.findByAddress(defaultFirestation().getAddress()))
                .thenReturn(Optional.empty());
        assertThrows(FirestationAddressNotFoundException.class,
                () -> firestationService.removeFirestation(firestationToRemove));
    }

    @Test
    public void testDeleteFirestationStationNotFound() {
        Firestation firestationToRemove = Firestation.builder().station(defaultFirestation().getStation()).build();
        when(firestationRepository.findAllByStation(defaultFirestation().getStation()))
                .thenReturn(new HashSet<Firestation>());
        assertThrows(FirestationStationNotFoundException.class,
                () -> firestationService.removeFirestation(firestationToRemove));
    }

    @Test
    public void testDeleteFirestationBadRequest() {
        Firestation firestationToRemove = Firestation.builder().build();
        assertThrows(FirestationBadRequestException.class,
                () -> firestationService.removeFirestation(firestationToRemove));
    }

}
