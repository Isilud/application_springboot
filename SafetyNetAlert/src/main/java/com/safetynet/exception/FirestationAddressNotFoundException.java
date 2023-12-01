package com.safetynet.exception;

import com.safetynet.model.Firestation;

public class FirestationAddressNotFoundException extends Exception {

    public FirestationAddressNotFoundException(Firestation firestation) {
        super("No Firestation with address " + firestation.getAddress() + " found");
    }

}
