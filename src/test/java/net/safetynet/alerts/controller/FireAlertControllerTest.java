package net.safetynet.alerts.controller;

import net.safetynet.alerts.dto.FireAlertDTO;
import net.safetynet.alerts.entity.Person;
import net.safetynet.alerts.service.FireAlertService;
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
public class FireAlertControllerTest {
    @Mock
    private FireAlertService fireAlertService;

    @InjectMocks
    private FireAlertController fireAlertController;

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

    @DisplayName("JUnit test for getFireAlert method")
    @Test
    public void givenAddress_whenGetFireAlert_thenReturnFireAlertDTOList() {
        List<FireAlertDTO> fireAlertDTOList = new ArrayList<>();

        given(fireAlertService.getFireStationAndPersonsByAddress(person.getAddress())).willReturn(fireAlertDTOList);

        List<FireAlertDTO> returnedFireAlertDTOList = fireAlertController.getFireAlert(person.getAddress());
        assertThat(returnedFireAlertDTOList).isEqualTo(fireAlertDTOList);
    }
}
