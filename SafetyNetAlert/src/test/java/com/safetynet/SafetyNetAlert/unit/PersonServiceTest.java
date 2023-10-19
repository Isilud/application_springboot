package com.safetynet.SafetyNetAlert.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.exception.PersonAlreadyExistException;
import com.safetynet.exception.PersonBadRequestException;
import com.safetynet.exception.PersonNotFoundException;
import com.safetynet.model.Person;
import com.safetynet.repository.PersonRepository;
import com.safetynet.service.PersonService;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    private PersonService personService;

    public Person defaultPerson() {
        return Person.builder()
                .firstName("firstName")
                .lastName("lastName")
                .address("address")
                .city("city")
                .zip("1234")
                .phone("0123456")
                .email("email")
                .build();
    }

    @BeforeEach
    public void clear() {
        personService = new PersonService(personRepository);
    }

    @Test
    public void testSavePerson() throws PersonAlreadyExistException {
        when(personRepository.findByName(defaultPerson().getFirstName(), defaultPerson().getLastName())).thenReturn(Optional.empty());
        personService.savePerson(defaultPerson());
        verify(personRepository).save(defaultPerson());
    }

    @Test
    public void testSaveExistingPerson() {
        when(personRepository.findByName(defaultPerson().getFirstName(), defaultPerson().getLastName())).thenReturn(Optional.of(defaultPerson()));
        assertThrows(PersonAlreadyExistException.class, () -> personService.savePerson(defaultPerson()));
    }

    @Test
    public void testGetPersons() {
        Set<Person> expectedList = new HashSet<Person>();
        expectedList.add(defaultPerson());
        when(personRepository.getAll()).thenReturn(expectedList);
        Set<Person> registeredPersons = personService.getAllPersons();
        assertEquals(registeredPersons, expectedList);
    }

    @Test
    public void testUpdatePerson() throws PersonNotFoundException {
        Person initialPerson = defaultPerson();
        Person updatedPerson = defaultPerson();
        updatedPerson.setCity("anotherCity");
        when(personRepository.findByName(initialPerson.getFirstName(), initialPerson.getLastName()))
                .thenReturn(Optional.of(initialPerson));
        personService.updatePerson(updatedPerson);
        verify(personRepository).remove(initialPerson);
        verify(personRepository).save(updatedPerson);
    }

    @Test
    public void testUpdatePersonNotFound() {
        when(personRepository.findByName(defaultPerson().getFirstName(), defaultPerson().getLastName()))
                .thenReturn(Optional.empty());
        assertThrows(PersonNotFoundException.class, () -> personService.updatePerson(defaultPerson()));
    }

    @Test
    public void testDeletePerson() throws PersonNotFoundException, PersonBadRequestException {
        when(personRepository.findByName(defaultPerson().getFirstName(), defaultPerson().getLastName()))
                .thenReturn(Optional.of(defaultPerson()));
        personService.removePerson(defaultPerson());
        verify(personRepository).remove(defaultPerson());
    }

    @Test
    public void testDeletePersonNotFound() {
        when(personRepository.findByName(defaultPerson().getFirstName(), defaultPerson().getLastName()))
                .thenReturn(Optional.empty());
        assertThrows(PersonNotFoundException.class,
                () -> personService.removePerson(defaultPerson()));
    }

    @Test
    public void testDeletePersonBadRequest() {
        Person personToRemove = Person.builder().build();
        assertThrows(PersonBadRequestException.class,
                () -> personService.removePerson(personToRemove));
    }

}
