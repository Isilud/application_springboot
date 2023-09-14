package com.safetynet.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public interface DataService {

    @PostConstruct
    public void setUp() throws IOException;

    public String toString();

}
