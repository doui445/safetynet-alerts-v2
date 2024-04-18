package net.safetynet.alerts.service;

import net.safetynet.alerts.entity.Person;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface PersonService {
    Person savePerson(Person person);

    Optional<Person> getPersonByFirstNameAndLastName(String firstName, String lastName);

    ResponseEntity<Person> updatePerson(Person person);

    void deletePerson(String firstName, String lastName);
}
