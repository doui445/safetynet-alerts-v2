package net.safetynet.alerts.repository;

import net.safetynet.alerts.entity.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PersonRepositoryTest {
    @Autowired
    PersonRepository personRepository;

    private Person person;

    @BeforeEach
    public void setUp() {
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

    @DisplayName("JUnit test for save person operation")
    @Test
    public void givenPersonObject_whenSave_thenReturnSavedPerson() {
        Person savedPerson = personRepository.save(person);
        assertThat(savedPerson).isNotNull();
        assertThat(savedPerson.getId()).isGreaterThan(0);
    }

    @DisplayName("JUnit test for get all persons operation")
    @Test
    public void givenPersonsList_whenFindAll_thenPersonsList() {
        List<Person> oldPersonList = personRepository.findAll();
        Person person1 = Person.builder()
                .firstName("John")
                .lastName("Cena")
                .address("225 Donald Road")
                .city("Culver")
                .zip("97451")
                .phone("0123456789")
                .email("cena@gmail,com")
                .build();
        personRepository.save(person);
        personRepository.save(person1);

        List<Person> newPersonList = personRepository.findAll();
        newPersonList.removeAll(oldPersonList);

        assertThat(newPersonList).isNotNull();
        assertThat(newPersonList.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for get person by id operation")
    @Test
    public void givenPersonObject_whenFindById_thenReturnPersonObject() {
        personRepository.save(person);
        Optional<Person> optionalPerson = personRepository.findById(person.getId());
        assertThat(optionalPerson.isPresent()).isTrue();
        Person personDB = optionalPerson.get();
        assertThat(personDB).isNotNull();
    }

    @DisplayName("JUnit test for update person operation")
    @Test
    public void givenPersonObject_whenUpdatePerson_thenReturnUpdatedPerson() {

        personRepository.save(person);

        Optional<Person> optionalPerson = personRepository.findById(person.getId());
        assertThat(optionalPerson.isPresent()).isTrue();

        Person savedPerson = optionalPerson.get();
        savedPerson.setEmail("ram@gmail.com");
        savedPerson.setFirstName("Ram");
        Person updatedPerson =  personRepository.save(savedPerson);


        assertThat(updatedPerson.getEmail()).isEqualTo("ram@gmail.com");
        assertThat(updatedPerson.getFirstName()).isEqualTo("Ram");
    }

    @DisplayName("JUnit test for delete person operation")
    @Test
    public void givenPersonObject_whenDelete_thenRemovePerson() {

        personRepository.save(person);

        personRepository.deleteById(person.getId());
        Optional<Person> optionalPerson = personRepository.findById(person.getId());

        assertThat(optionalPerson).isEmpty();
    }
}
