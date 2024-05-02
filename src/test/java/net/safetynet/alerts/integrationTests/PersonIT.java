package net.safetynet.alerts.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.safetynet.alerts.entity.Person;
import net.safetynet.alerts.service.PersonServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PersonIT {
    @Autowired
    MockMvc mvc;

    @MockBean
    PersonServiceImpl personService;

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

    @DisplayName("JUnit test for create person REST API")
    @Test
    public void givenPersonObject_whenCreatePerson_thenReturnSavedPerson() {
        Mockito.when(personService.savePerson(ArgumentMatchers.any())).thenReturn(person);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            mvc.perform(MockMvcRequestBuilders.post("/person")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(person)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.firstName", Matchers.equalTo("Steve")))
                    .andExpect(jsonPath("$.lastName", Matchers.equalTo("Lander")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("JUnit test for update person REST API - positive scenario")
    @Test
    public void givenUpdatedPerson_whenUpdatePerson_thenReturnUpdatedPersonObject() {
        Person person1 = Person.builder()
                .firstName("Steve")
                .lastName("Lander")
                .address("225 Donald Road")
                .city("Culver")
                .zip("97451")
                .phone("0654321987")
                .email("steve.lander@icloud.com")
                .build();

        given(personService.getPersonByFirstNameAndLastName(person.getFirstName(), person.getLastName())).willReturn(Optional.of(person));
        Mockito.when(personService.updatePerson(ArgumentMatchers.any())).thenReturn(new ResponseEntity<>(person1, HttpStatus.OK));
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            mvc.perform(MockMvcRequestBuilders.put("/person")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(person1)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstName", Matchers.equalTo("Steve")))
                    .andExpect(jsonPath("$.lastName", Matchers.equalTo("Lander")))
                    .andExpect(jsonPath("$.address", Matchers.equalTo("225 Donald Road")))
                    .andExpect(jsonPath("$.phone", Matchers.equalTo("0654321987")))
                    .andExpect(jsonPath("$.email", Matchers.equalTo("steve.lander@icloud.com")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("JUnit test for update person REST API - negative scenario")
    @Test
    public void givenUpdatedPerson_whenUpdatePerson_thenReturn404() {
        Person person1 = Person.builder()
                .firstName("Steve")
                .lastName("Lander")
                .address("225 Donald Road")
                .city("Culver")
                .zip("97451")
                .phone("0654321987")
                .email("steve.lander@icloud.com")
                .build();

        given(personService.getPersonByFirstNameAndLastName(person.getFirstName(), person.getLastName())).willReturn(Optional.empty());
        //Mockito.when(personService.updatePerson(ArgumentMatchers.any())).thenReturn(person1);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            mvc.perform(MockMvcRequestBuilders.put("/person")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(person1)))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("JUnit test for delete person REST API")
    @Test
    public void givenPersonObject_whenDeletePerson_thenReturn200() {
        try {
            mvc.perform(MockMvcRequestBuilders.delete("/person")
                            .param("firstName", "Steve")
                            .param("lastName", "Lander"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
