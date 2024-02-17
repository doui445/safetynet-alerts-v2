package net.safetynet.alerts.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.safetynet.alerts.entity.FireStation;
import net.safetynet.alerts.service.FireStationServiceImpl;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class FireStationIT {
    @Autowired
    MockMvc mvc;

    @MockBean
    FireStationServiceImpl fireStationService;

    private FireStation fireStation;

    @BeforeEach
    public void setUpPerTest() {
        fireStation = FireStation.builder()
                .address("123 Donald Avenue")
                .station("10")
                .build();
    }

    @DisplayName("JUnit test for create fire station REST API")
    @Test
    public void givenFireStationObject_whenCreateFireStation_thenReturnSavedFireStation() {
        Mockito.when(fireStationService.saveFireStation(ArgumentMatchers.any())).thenReturn(fireStation);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            mvc.perform(MockMvcRequestBuilders.post("/firestation")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(fireStation)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.station", Matchers.equalTo("10")))
                    .andExpect(jsonPath("$.address", Matchers.equalTo("123 Donald Avenue")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("JUnit test for update fire station REST API - positive scenario")
    @Test
    public void givenUpdatedFireStation_whenUpdateFireStation_thenReturnUpdatedFireStationObject() {
        FireStation fireStation1 = FireStation.builder()
                .address("225 Donald Road")
                .station("10")
                .build();

        given(fireStationService.getFireStationByStation(fireStation.getStation())).willReturn(Optional.of(fireStation));
        Mockito.when(fireStationService.updateFireStation(ArgumentMatchers.any())).thenReturn(fireStation1);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            mvc.perform(MockMvcRequestBuilders.put("/firestation")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(fireStation1)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.station", Matchers.equalTo("10")))
                    .andExpect(jsonPath("$.address", Matchers.equalTo("225 Donald Road")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("JUnit test for update fire station REST API - negative scenario")
    @Test
    public void givenUpdatedFireStation_whenUpdateFireStation_thenReturn404() {
        FireStation fireStation1 = FireStation.builder()
                .address("225 Donald Road")
                .station("10")
                .build();

        given(fireStationService.getFireStationByStation(fireStation.getStation())).willReturn(Optional.empty());
        Mockito.when(fireStationService.updateFireStation(ArgumentMatchers.any())).thenReturn(fireStation1);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            mvc.perform(MockMvcRequestBuilders.put("/firestation")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(fireStation1)))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("JUnit test for delete fire station REST API")
    @Test
    public void givenFireStationObject_whenDeleteFireStation_thenReturn200() {
        doNothing().when(fireStationService).deleteFireStation(fireStation.getStation());
        try {
            mvc.perform(MockMvcRequestBuilders.delete("/firestation/10")).andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
