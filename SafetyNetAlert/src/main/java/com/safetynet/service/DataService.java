package com.safetynet.service;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public interface DataService {

    @PostConstruct
    public void setUp();

    public String toString();

}
