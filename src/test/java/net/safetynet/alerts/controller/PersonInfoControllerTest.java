package net.safetynet.alerts.controller;

import net.safetynet.alerts.dto.ChildAlertDTO;
import net.safetynet.alerts.dto.PersonInfoDTO;
import net.safetynet.alerts.entity.Person;
import net.safetynet.alerts.service.ChildAlertService;
import net.safetynet.alerts.service.PersonInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class PersonInfoControllerTest {
    @Mock
    private PersonInfoService personInfoService;

    @InjectMocks
    private PersonInfoController personInfoController;

    private Person person;

    @BeforeEach
    public void SetUp() {
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

    @DisplayName("JUnit test for getPersonInfo method")
    @Test
    public void givenAddress_whenGetPersonInfo_thenReturnPersonInfoDTO() {
        PersonInfoDTO personInfoDTO = new PersonInfoDTO();
        personInfoDTO.setLastName(person.getLastName());
        personInfoDTO.setAddress(person.getAddress());
        personInfoDTO.setAge(22);
        personInfoDTO.setEmail(person.getEmail());

        given(personInfoService.getPersonInfoByFirstNameAndLastName(person.getFirstName(), person.getLastName())).willReturn(personInfoDTO);

        PersonInfoDTO returnedPersonInfoDTO = personInfoController.getPersonInfo(person.getFirstName(), person.getLastName());
        assertThat(returnedPersonInfoDTO).isEqualTo(personInfoDTO);
    }
}
