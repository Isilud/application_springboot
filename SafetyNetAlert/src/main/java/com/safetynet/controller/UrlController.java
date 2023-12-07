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

import com.safetynet.dto.FireInformationDTO;
import com.safetynet.dto.PersonDTO;
import com.safetynet.dto.PersonsInCoverageSummaryDTO;
import com.safetynet.exception.FirestationAddressNotFoundException;
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
        logger.debug("Fetching people covered with station : " + stationNumber);
        PersonsInCoverageSummaryDTO response = urlService.getPeopleWithStationCoverage(stationNumber);
        logger.info("Peoples found : " + response);
        return response;
    }

    @GetMapping(path = "/childAlert", params = "address")
    @ResponseStatus(HttpStatus.OK)
    public Set<PersonDTO> getChildrenAtAddress(
            @RequestParam(name = "address", required = true) String address) throws MedicalRecordNotFoundException {
        logger.debug("Fetching children with address : " + address);
        Set<PersonDTO> response = urlService.getChildrenAtAddress(address);
        logger.info("Childrens found : " + response);
        return response;
    }

    @GetMapping(path = "/phoneAlert", params = "firestation")
    @ResponseStatus(HttpStatus.OK)
    public Set<String> getPhoneWithStationCoverage(
            @RequestParam(name = "firestation", required = true) String station)
            throws FirestationStationNotFoundException {
        logger.debug("Fetching phone covered by station : " + station);
        Set<String> response = urlService.getPhoneUnderStation(station);
        logger.info("Phone numbers found : " + response);
        return response;
    }

    @GetMapping(path = "/fire", params = "address")
    @ResponseStatus(HttpStatus.OK)
    public FireInformationDTO getFireInformation(
            @RequestParam(name = "address", required = true) String address)
            throws FirestationAddressNotFoundException, MedicalRecordNotFoundException {
        logger.debug("Fetching information for fire at address : " + address);
        FireInformationDTO response = urlService.getFireInformation(address);
        logger.info("Retrieved information : " + response);
        return response;
    }

    @GetMapping(path = "/flood", params = "stations")
    @ResponseStatus(HttpStatus.OK)
    public List<PersonsInCoverageSummaryDTO> getFloodCoverage(
            @RequestParam(name = "stations", required = true) List<String> stations)
            throws FirestationAddressNotFoundException, MedicalRecordNotFoundException,
            FirestationStationNotFoundException {
        logger.debug("Fetching information for flood covered by stations : " + stations);
        List<PersonsInCoverageSummaryDTO> response = urlService.getFloodCoverage(stations);
        logger.info("Retrieved information : " + response);
        return response;
    }

    @GetMapping(path = "/personInfo", params = { "firstName", "lastName" })
    @ResponseStatus(HttpStatus.OK)
    public List<PersonDTO> getPersonInfo(
            @RequestParam(name = "firstName", required = true) String firstName,
            @RequestParam(name = "lastName", required = true) String lastName)
            throws FirestationAddressNotFoundException, MedicalRecordNotFoundException,
            FirestationStationNotFoundException {
        logger.debug("Fetching information for habitant named : " + firstName + " " + lastName);
        List<PersonDTO> response = urlService.getPersonInfo(firstName, lastName);
        logger.info("Retrieved information : " + response);
        return response;
    }

    @GetMapping(path = "/communityEmail", params = "city")
    @ResponseStatus(HttpStatus.OK)
    public Set<String> personEmailFromCity(
            @RequestParam(name = "city", required = true) String city)
            throws FirestationAddressNotFoundException, MedicalRecordNotFoundException,
            FirestationStationNotFoundException {
        logger.debug("Fetching email for habitant from : " + city);
        Set<String> response = urlService.personEmailFromCity(city);
        logger.info("Retrieved information : " + response);
        return response;
    }
}