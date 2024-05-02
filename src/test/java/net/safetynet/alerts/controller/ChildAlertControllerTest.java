package net.safetynet.alerts.controller;

import net.safetynet.alerts.dto.ChildAlertDTO;
import net.safetynet.alerts.entity.Person;
import net.safetynet.alerts.service.ChildAlertService;
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
public class ChildAlertControllerTest {
    @Mock
    private ChildAlertService childAlertService;

    @InjectMocks
    private ChildAlertController childAlertController;

    private Person person;
    private ChildAlertDTO childAlert;

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

        childAlert = new ChildAlertDTO();
        childAlert.setFirstName(person.getFirstName());
        childAlert.setLastName(person.getLastName());
        childAlert.setAge(22);
    }

    @DisplayName("JUnit test for getAllChildLivingAtAddress method")
    @Test
    public void givenAddress_whenGetAllChildLivingAtAddress_thenReturnChildAlertDTOList() {
        List<ChildAlertDTO> childAlertDTOList = new ArrayList<>();
        childAlertDTOList.add(childAlert);

        given(childAlertService.getAllChildLivingAtAddress(person.getAddress())).willReturn(childAlertDTOList);

        List<ChildAlertDTO> returnedChildAlertDTOList = childAlertController.getChildAlert(person.getAddress());
        assertThat(returnedChildAlertDTOList).isEqualTo(childAlertDTOList);
    }
}
