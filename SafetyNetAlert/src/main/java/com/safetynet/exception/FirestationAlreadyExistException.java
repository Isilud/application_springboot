package com.safetynet.exception;

import com.safetynet.model.Firestation;

public class FirestationAlreadyExistException extends Exception {

    public FirestationAlreadyExistException(Firestation firestation) {
        super("Firestation with address " + firestation.getAddress() + " already exist.");
    }

}
