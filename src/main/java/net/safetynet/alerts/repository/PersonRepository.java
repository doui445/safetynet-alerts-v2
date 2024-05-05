package net.safetynet.alerts.repository;

import net.safetynet.alerts.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findByFirstNameEqualsAndLastNameEquals(String firstName, String lastName);

    @Query("SELECT p.email FROM Person p WHERE p.city = ?1")
    List<String> findEmailByCity(String city);

    void deleteByFirstNameEqualsAndLastNameEquals(String firstName, String Lastname);

    @Query("SELECT a FROM Person a WHERE a.address = ?1")
    List<Person> findAllByAddress(String address);
}
