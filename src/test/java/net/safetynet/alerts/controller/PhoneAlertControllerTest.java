package net.safetynet.alerts.controller;

import net.safetynet.alerts.entity.Person;
import net.safetynet.alerts.service.FireStationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class PhoneAlertControllerTest {
    @Mock
    private FireStationService fireStationService;

    @InjectMocks
    private PhoneAlertController phoneAlertController;

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

    @DisplayName("JUnit test for getPhoneOfPeopleByFirestation method")
    @Test
    public void givenAddress_whenGetPhoneOfPeopleByFirestation_thenReturnListOfPhoneNumbers() {
        List<String> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(person.getPhone());

        given(fireStationService.getPhoneNumberOfAllPersonsCoveredByStation(person.getAddress())).willReturn(phoneNumbers);

        List<String> returnedPhoneNumbers = phoneAlertController.getPhoneOfPeopleByFirestation(person.getAddress());
        assertThat(returnedPhoneNumbers).isEqualTo(phoneNumbers);
    }
}
