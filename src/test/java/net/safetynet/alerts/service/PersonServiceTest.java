package net.safetynet.alerts.service;

import net.safetynet.alerts.entity.Person;
import net.safetynet.alerts.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

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

    @DisplayName("JUnit test for savePerson method")
    @Test
    public void givenPersonObject_whenSavePerson_thenReturnPersonObject() {
        given(personRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName()))
                .willReturn(Optional.empty());

        given(personRepository.save(person)).willReturn(person);

        System.out.println(personRepository);
        System.out.println(personService);

        Person savedPerson = personService.savePerson(person);

        System.out.println(savedPerson);
        assertThat(savedPerson).isNotNull();
    }

    @DisplayName("JUnit test for savePerson method which throws exception")
    @Test
    public void givenExistingPerson_whenSavePerson_thenReturnNull() {
        given(personRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName()))
                .willReturn(Optional.of(person));

        System.out.println(personRepository);
        System.out.println(personService);

        assertNull(personService.savePerson(person));

        verify(personRepository, never()).save(any(Person.class));
    }

    @DisplayName("JUnit test for getPersonByFirstNameAndLastName method")
    @Test
    public void givenPersonFirstNameAndLastName_whenGetPersonByFirstNameAndLastName_thenReturnPersonObject() {
        given(personRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName()))
                .willReturn(Optional.of(person));

        Person returnedPerson = personService.getPersonByFirstNameAndLastName(person.getFirstName(), person.getLastName()).get();

        assertThat(returnedPerson).isNotNull();
    }

    @DisplayName("JUnit test for updatePerson method")
    @Test
    public void givenPersonObject_whenUpdatePerson_thenReturnUpdatedPerson() {
        given(personRepository.save(person)).willReturn(person);
        person.setEmail("ram@gmail.com");
        person.setFirstName("Ram");

        Person updatedPerson = personService.updatePerson(person);

        assertThat(updatedPerson.getEmail()).isEqualTo("ram@gmail.com");
        assertThat(updatedPerson.getFirstName()).isEqualTo("Ram");
    }

    @DisplayName("JUnit test for deletePerson method")
    @Test
    public void givenPersonFirstNameAndLastName_whenDeletePerson_thenNothing() {
        String personFirstName = "Steve";
        String personLastName = "Lander";

        willDoNothing().given(personRepository).deleteByFirstNameAndLastName(personFirstName, personLastName);

        personService.deletePerson(personFirstName, personLastName);

        verify(personRepository, times(1)).deleteByFirstNameAndLastName(personFirstName, personLastName);
    }

    @DisplayName("JUnit test for getEmailForAllPersonsInCity method")
    @Test
    public void givenCity_whenGetEmailForAllPersonsInCity_thenReturnEmailList() {
        List<String> EmailList = new ArrayList<>();
        EmailList.add(person.getEmail());

        given(personRepository.findEmailByCity(person.getCity())).willReturn(EmailList);

        List<String> EmailsOfPersonInCity = personService.getEmailForAllPersonsInCity(person.getCity());

        assertThat(EmailsOfPersonInCity).isEqualTo(EmailList);
    }
}
