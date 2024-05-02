package net.safetynet.alerts.controller;

import net.safetynet.alerts.entity.Person;
import net.safetynet.alerts.service.PersonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {
    @Mock
    private PersonServiceImpl personService;

    @InjectMocks
    private PersonController personController;

    private Person person;

    @BeforeEach
    public void setup() {
        person = Person.builder()
                .firstName("Steve")
                .lastName("Lander")
                .address("123 Donald Avenue")
                .city("Culver")
                .zip("97451")
                .phone("0123456789")
                .email("steve.lander@gmail.com")
                .build();
    }

    @DisplayName("JUnit test for createPerson method")
    @Test
    public void givenPersonObject_whenCreatePerson_thenReturnPersonObject() {
        given(personService.savePerson(person))
                .willReturn(person);
        Person savedPerson = personController.createPerson(person);
        assertThat(savedPerson).isEqualTo(person);
    }

    @DisplayName("JUnit test for updatePerson method")
    @Test
    public void givenPersonObject_whenUpdatePerson_thenReturnResponseEntityOfPerson() {
        given(personService.updatePerson(person))
                .willReturn(new ResponseEntity<>(person, HttpStatus.OK));
        ResponseEntity<Person> updatedPerson = personController.updatePerson(person);
        assertThat(updatedPerson.getBody()).isEqualTo(person);
        assertThat(updatedPerson.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("JUnit test for deletePerson method")
    @Test
    public void givenPersonObject_whenDeletePerson_thenReturnResponseEntityOK() {
        doNothing().when(personService).deletePerson(person.getFirstName(), person.getLastName());
        ResponseEntity<String> isDeleted = personController.deletePerson(person.getFirstName(), person.getLastName());
        assertThat(isDeleted.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
