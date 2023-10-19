package com.safetynet.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.safetynet.exception.PersonBadRequestException;
import com.safetynet.exception.FirestationAlreadyExistException;
import com.safetynet.exception.FirestationBadRequestException;
import com.safetynet.exception.FirestationStationNotFoundException;
import com.safetynet.exception.FirestationAddressNotFoundException;
import com.safetynet.exception.MedicalRecordAlreadyExistException;
import com.safetynet.exception.MedicalRecordBadRequestException;
import com.safetynet.exception.MedicalRecordNotFoundException;
import com.safetynet.exception.PersonAlreadyExistException;
import com.safetynet.exception.PersonNotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {

  Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

  @ExceptionHandler(PersonBadRequestException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public String exceptionError(PersonBadRequestException ex) {
    logger.error(ex.getMessage());
    return ex.getMessage();
  }

  @ExceptionHandler(PersonNotFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public String exceptionError(PersonNotFoundException ex) {
    logger.error(ex.getMessage());
    return ex.getMessage();
  }

  @ExceptionHandler(PersonAlreadyExistException.class)
  @ResponseStatus(value = HttpStatus.CONFLICT)
  public String exceptionError(PersonAlreadyExistException ex) {
    logger.error(ex.getMessage());
    return ex.getMessage();
  }

  @ExceptionHandler(FirestationBadRequestException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public String exceptionError(FirestationBadRequestException ex) {
    logger.error(ex.getMessage());
    return ex.getMessage();
  }

  @ExceptionHandler(FirestationAddressNotFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public String exceptionError(FirestationAddressNotFoundException ex) {
    logger.error(ex.getMessage());
    return ex.getMessage();
  }

  @ExceptionHandler(FirestationStationNotFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public String exceptionError(FirestationStationNotFoundException ex) {
    logger.error(ex.getMessage());
    return ex.getMessage();
  }

  @ExceptionHandler(FirestationAlreadyExistException.class)
  @ResponseStatus(value = HttpStatus.CONFLICT)
  public String exceptionError(FirestationAlreadyExistException ex) {
    logger.error(ex.getMessage());
    return ex.getMessage();
  }

  @ExceptionHandler(MedicalRecordBadRequestException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public String exceptionError(MedicalRecordBadRequestException ex) {
    logger.error(ex.getMessage());
    return ex.getMessage();
  }

  @ExceptionHandler(MedicalRecordNotFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public String exceptionError(MedicalRecordNotFoundException ex) {
    logger.error(ex.getMessage());
    return ex.getMessage();
  }

  @ExceptionHandler(MedicalRecordAlreadyExistException.class)
  @ResponseStatus(value = HttpStatus.CONFLICT)
  public String exceptionError(MedicalRecordAlreadyExistException ex) {
    logger.error(ex.getMessage());
    return ex.getMessage();
  }
}
