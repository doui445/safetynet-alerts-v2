package net.safetynet.alerts.repository;

import net.safetynet.alerts.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByFirstNameAndLastName(String firstName, String lastName);

    List<String> findEmailByCity(String city);

    void deleteByFirstNameAndLastName(String firstName, String Lastname);

    List<Person> findAllByAddress(String address);
}
