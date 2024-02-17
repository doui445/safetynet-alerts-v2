package net.safetynet.alerts.controller;

import lombok.AllArgsConstructor;
import net.safetynet.alerts.entity.Person;
import net.safetynet.alerts.service.PersonServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/person")
public class PersonController {

    private PersonServiceImpl personService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Person createPerson(@RequestBody Person person) {
        return personService.savePerson(person);
    }

    @PutMapping
    public ResponseEntity<Person> updatePerson(@RequestBody Person person) {
        return personService.getPersonByFirstNameAndLastName(person.getFirstName(), person.getLastName())
                .map(savedPerson -> {
                    savedPerson.setAddress(person.getAddress());
                    savedPerson.setCity(person.getCity());
                    savedPerson.setZip(person.getZip());
                    savedPerson.setPhone(person.getPhone());
                    savedPerson.setEmail(person.getEmail());

                    Person updatedPerson = personService.updatePerson(savedPerson);
                    return new ResponseEntity<>(updatedPerson, HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public ResponseEntity<String> deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
        personService.deletePerson(firstName, lastName);
        return ResponseEntity.status(HttpStatus.OK).body("Person " + firstName + lastName + " deleted successfully!");
    }
}
