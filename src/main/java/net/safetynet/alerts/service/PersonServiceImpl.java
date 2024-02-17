package net.safetynet.alerts.service;

import lombok.AllArgsConstructor;
import net.safetynet.alerts.entity.Person;
import net.safetynet.alerts.repository.PersonRepository;
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
        Optional<Person> savedPerson = personRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
        if(savedPerson.isPresent()){
            System.out.println("Person already exist with given email:" + person.getEmail());
            return null;
        }
        return personRepository.save(person);
    }

    @Override
    public Optional<Person> getPersonByFirstNameAndLastName(String firstName, String lastName) {
        return personRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public Person updatePerson(Person person) {
        return personRepository.save(person);
    }

    @Override
    public void deletePerson(String firstName, String lastName) {
        personRepository.deleteByFirstNameAndLastName(firstName, lastName);
    }
}
