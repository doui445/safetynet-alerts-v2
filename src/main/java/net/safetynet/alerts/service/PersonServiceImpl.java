package net.safetynet.alerts.service;

import lombok.AllArgsConstructor;
import net.safetynet.alerts.entity.Person;
import net.safetynet.alerts.repository.PersonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PersonServiceImpl implements PersonService {
    private PersonRepository personRepository;

    public List<String> getEmailForAllPersonsInCity(String city) {
        return personRepository.findEmailByCity(city);
    }

    @Override
    public Person savePerson(Person person) {
        Optional<Person> savedPerson = Optional.ofNullable(personRepository.findByFirstNameEqualsAndLastNameEquals(person.getFirstName(), person.getLastName()));
        if(savedPerson.isPresent()){
            System.out.println("Person already exist with given email:" + person.getEmail());
            return null;
        }
        return personRepository.save(person);
    }

    @Override
    public Optional<Person> getPersonByFirstNameAndLastName(String firstName, String lastName) {
        return Optional.ofNullable(personRepository.findByFirstNameEqualsAndLastNameEquals(firstName, lastName));
    }

    @Override
    public ResponseEntity<Person> updatePerson(Person person) {
        return getPersonByFirstNameAndLastName(person.getFirstName(), person.getLastName())
                .map(savedPerson -> {
                    savedPerson.setAddress(person.getAddress());
                    savedPerson.setCity(person.getCity());
                    savedPerson.setZip(person.getZip());
                    savedPerson.setPhone(person.getPhone());
                    savedPerson.setEmail(person.getEmail());

                    Person updatedPerson = personRepository.save(savedPerson);
                    return new ResponseEntity<>(updatedPerson, HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public void deletePerson(String firstName, String lastName) {
        personRepository.deleteByFirstNameEqualsAndLastNameEquals(firstName, lastName);
    }
}
