package com.safetynet.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.safetynet.model.BadRequestException;

@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public String resourceNotFoundException(ResourceNotFoundException ex) {
    return "Resource not found : " + ex.getMessage();
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  @ResponseStatus(value = HttpStatus.CONFLICT)
  public String resourceHasConflict(DataIntegrityViolationException ex) {
    return "Request cause conflict : " + ex.getMessage();
  }

  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public String exceptionError(BadRequestException ex) {
    return "Wrong request : " + ex.getMessage();
  }
}
