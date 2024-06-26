package net.safetynet.alerts.service;

import net.safetynet.alerts.entity.FireStation;
import net.safetynet.alerts.repository.FireStationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FireStationServiceTest {
    @Mock
    private FireStationRepository fireStationRepository;

    @InjectMocks
    private FireStationServiceImpl fireStationService;

    private FireStation fireStation;

    @BeforeEach
    public void setup() {
        fireStation = FireStation.builder()
                .address("123 Donald Avenue")
                .station("10")
                .build();
    }

    @DisplayName("JUnit test for saveFireStation method")
    @Test
    public void givenFireStationObject_whenSaveFireStation_thenReturnFireStationObject() {
        given(fireStationRepository.findByStation(fireStation.getStation()))
                .willReturn(new ArrayList<>());

        given(fireStationRepository.save(fireStation)).willReturn(fireStation);

        System.out.println(fireStationRepository);
        System.out.println(fireStationService);

        FireStation savedFireStation = fireStationService.saveFireStation(fireStation);

        System.out.println(savedFireStation);
        assertThat(savedFireStation).isNotNull();
    }

    @DisplayName("JUnit test for saveFireStation method which throws exception")
    @Test
    public void givenExistingFireStation_whenSaveFireStation_thenReturnNull() {
        given(fireStationRepository.findByStation(fireStation.getStation()))
                .willReturn(List.of(fireStation));

        System.out.println(fireStationRepository);
        System.out.println(fireStationService);

        assertNull(fireStationService.saveFireStation(fireStation));

        verify(fireStationRepository, never()).save(any(FireStation.class));
    }

    @DisplayName("JUnit test for getFireStationByStation method")
    @Test
    public void givenFireStationStation_whenGetFireStationByStation_thenReturnFireStationObject() {
        given(fireStationRepository.findByStation(fireStation.getStation()))
                .willReturn(List.of(fireStation));

        FireStation returnedFireStation = fireStationService.getFireStationByStation(fireStation.getStation()).getFirst();

        assertThat(returnedFireStation).isNotNull();
    }

    @DisplayName("JUnit test for updateFireStation method")
    @Test
    public void givenFireStationObject_whenUpdateFireStation_thenReturnUpdatedFireStation() {
        FireStation fireStation1 = fireStation;
        fireStation1.setId(20L);
        fireStation1.setAddress("225 Donald Road");
        fireStation1.setStation("11");

        given(fireStationRepository.findById(fireStation1.getId())).willReturn(Optional.of(fireStation1));

        ResponseEntity<FireStation> updatedFireStation = fireStationService.updateFireStation(fireStation1);

        assertThat(updatedFireStation.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("JUnit test for deleteFireStation method")
    @Test
    public void givenFireStationId_whenDeleteFireStation_thenNothing() {
        String fireStationStation = "10";

        willDoNothing().given(fireStationRepository).deleteByStationEquals(fireStationStation);

        fireStationService.deleteFireStationNumber(fireStationStation);

        verify(fireStationRepository, times(1)).deleteByStationEquals(fireStationStation);
    }

    @DisplayName("JUnit test for getPhoneNumberOfAllPersonsCoveredByStation method")
    @Test
    public void givenFireStationStation_whenGetPhoneNumberOfAllPersonsCoveredByStation_thenReturnPhoneNumberList() {
        List<String> phoneNumberList = new ArrayList<>();
        phoneNumberList.add("0123456789");

        given(fireStationRepository.findPhoneNumberByStation(fireStation.getStation())).willReturn(phoneNumberList);

        List<String> phoneNumberOfAllPersonsCoveredByStationList = fireStationService.getPhoneNumberOfAllPersonsCoveredByStation(fireStation.getStation());

        assertThat(phoneNumberOfAllPersonsCoveredByStationList).isEqualTo(phoneNumberList);
    }
}
