package com.safetynet.exception;

import com.safetynet.model.Firestation;

public class FirestationStationNotFoundException extends Exception {

    public FirestationStationNotFoundException(Firestation firestation) {
        super("No Firestation with station " + firestation.getStation() + " found");
    }

}
