package com.safetynet.controller;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.dto.ChildrenWithAddressDTO;
import com.safetynet.dto.FireInformationDTO;
import com.safetynet.dto.PersonsInCoverageSummaryDTO;
import com.safetynet.exception.FirestationStationNotFoundException;
import com.safetynet.exception.MedicalRecordNotFoundException;
import com.safetynet.service.UrlService;

@RestController
public class UrlController {

    @Autowired
    UrlService urlService;

    Logger logger = LoggerFactory.getLogger(MedicalRecordController.class);

    @GetMapping(path = "/firestation", params = "stationNumber")
    @ResponseStatus(HttpStatus.OK)
    public PersonsInCoverageSummaryDTO getPeopleWithStationCoverage(
            @RequestParam(name = "stationNumber", required = true) String stationNumber)
            throws FirestationStationNotFoundException, MedicalRecordNotFoundException {
        logger.info("Fetching people covered with station : " + stationNumber);
        PersonsInCoverageSummaryDTO response = urlService.getPeopleWithStationCoverage(stationNumber);
        logger.info("Peoples found : " + response);
        return response;
    }

    @GetMapping(path = "/childAlert", params = "address")
    @ResponseStatus(HttpStatus.OK)
    public Set<ChildrenWithAddressDTO> getChildrenAtAddress(
            @RequestParam(name = "address", required = true) String address) throws MedicalRecordNotFoundException {
        logger.info("Fetching children with address : " + address);
        Set<ChildrenWithAddressDTO> response = urlService.getChildrenAtAddress(address);
        logger.info("Childrens found : " + response);
        return response;
    }

    @GetMapping(path = "/phoneAlert", params = "firestation")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getPhoneWithStationCoverage(
            @RequestParam(name = "firestation", required = true) String station)
            throws FirestationStationNotFoundException {
        logger.info("Fetching phone covered by station : " + station);
        List<String> response = urlService.getPhoneUnderStation(station);
        logger.info("Phone numbers found : " + response);
        return response;
    }

    @GetMapping(path = "/fire", params = "address")
    @ResponseStatus(HttpStatus.OK)
    public FireInformationDTO getFireInformation(
            @RequestParam(name = "address", required = true) String address)
            throws FirestationStationNotFoundException {
        logger.info("Fetching information for fire at address : " + address);
        FireInformationDTO response = urlService.getFireInformation(address);
        logger.info("Retrieved information : " + response);
        return response;
    }
}