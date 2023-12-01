package com.safetynet.service;

import java.io.IOException;

import jakarta.annotation.PostConstruct;

public interface DataService {

    @PostConstruct
    public void setUp() throws IOException;

    public String toString();

}
