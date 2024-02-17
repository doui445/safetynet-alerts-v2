package net.safetynet.alerts.service;

import net.safetynet.alerts.entity.Person;

import java.util.Optional;

public interface PersonService {
    Person savePerson(Person person);

    Optional<Person> getPersonByFirstNameAndLastName(String firstName, String lastName);

    Person updatePerson(Person person);

    void deletePerson(String firstName, String lastName);
}
