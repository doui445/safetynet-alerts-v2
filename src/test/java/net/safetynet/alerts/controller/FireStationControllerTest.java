package net.safetynet.alerts.controller;

import net.safetynet.alerts.entity.FireStation;
import net.safetynet.alerts.service.FireStationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class FireStationControllerTest {
    @Mock
    private FireStationServiceImpl fireStationService;

    @InjectMocks
    private FireStationController fireStationController;

    private FireStation fireStation;

    @BeforeEach
    public void setup() {
        fireStation = FireStation.builder()
                .address("123 Donald Avenue")
                .station("10")
                .build();
    }

    @DisplayName("JUnit test for getFirestation method")
    @Test
    public void givenFireStationObject_whenGetFirestation_thenReturnOptionalOfFirestationObject() {
        given(fireStationService.getFireStationByStation(fireStation.getStation()))
                .willReturn(List.of(fireStation));
        List<FireStation> fireStations = fireStationController.getFirestation(fireStation.getStation());
        assertThat(fireStations.isEmpty()).isFalse();
    }

    @DisplayName("JUnit test for createFireStation method")
    @Test
    public void givenFireStationObject_whenCreateFireStation_thenReturnFirestationObject() {
        given(fireStationService.saveFireStation(fireStation))
                .willReturn(fireStation);
        FireStation savedFireStation = fireStationController.createFireStation(fireStation);
        assertThat(savedFireStation).isEqualTo(fireStation);
    }

    @DisplayName("JUnit test for updateFireStation method")
    @Test
    public void givenFireStationObject_whenUpdateFireStation_thenReturnResponseEntityOfFirestation() {
        given(fireStationService.updateFireStation(fireStation))
                .willReturn(new ResponseEntity<>(fireStation, HttpStatus.OK));
        ResponseEntity<FireStation> updatedFireStation = fireStationController.updateFireStation(fireStation);
        assertThat(updatedFireStation.getBody()).isEqualTo(fireStation);
        assertThat(updatedFireStation.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("JUnit test for deleteFireStation method")
    @Test
    public void givenFireStationObject_whenDeleteFireStation_thenReturnResponseEntityOK() {
        doNothing().when(fireStationService).deleteFireStationNumber(fireStation.getStation());
        ResponseEntity<String> isDeleted = fireStationController.deleteFireStation(fireStation.getStation());
        assertThat(isDeleted.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
