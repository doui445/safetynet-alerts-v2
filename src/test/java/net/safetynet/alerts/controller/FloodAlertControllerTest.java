package net.safetynet.alerts.controller;

import net.safetynet.alerts.dto.FloodAlertDTO;
import net.safetynet.alerts.dto.PersonInfoDTO;
import net.safetynet.alerts.entity.Person;
import net.safetynet.alerts.service.FloodAlertService;
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
public class FloodAlertControllerTest {
    @Mock
    private FloodAlertService floodAlertService;

    @InjectMocks
    private FloodAlertController floodAlertController;

    private List<String> stations;
    private List<List<Person>> personsInAddressList;
    private List<PersonInfoDTO> personInfoDTOList;

    @BeforeEach
    public void SetUp() {
        stations = new ArrayList<>();
        stations.add("1");

        personInfoDTOList = new ArrayList<>();
        personInfoDTOList.add(new PersonInfoDTO());
        personsInAddressList = new ArrayList<>();
        List<Person> persons = new ArrayList<>();
        persons.add(new Person());
        personsInAddressList.add(persons);
    }

    @DisplayName("JUnit test for getAddressAndPeopleByFloodStations method")
    @Test
    public void givenAddress_whenGetAddressAndPeopleByFloodStations_thenReturnFloodAlertDTOList() {
        List<FloodAlertDTO> floodAlertDTOList = new ArrayList<>();
        floodAlertDTOList.add(new FloodAlertDTO(personsInAddressList, personInfoDTOList));

        given(floodAlertService.getAddressAndPeopleByFloodStations(stations)).willReturn(floodAlertDTOList);

        List<FloodAlertDTO> returnedFloodAlertDTOList = floodAlertController.getAddressAndPeopleByFloodStations(stations);
        assertThat(returnedFloodAlertDTOList).isEqualTo(floodAlertDTOList);
    }
}
