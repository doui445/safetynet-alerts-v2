package net.safetynet.alerts.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.safetynet.alerts.entity.MedicalRecord;
import net.safetynet.alerts.service.MedicalRecordServiceImpl;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MedicalRecordIT {
    @Autowired
    MockMvc mvc;

    @MockBean
    MedicalRecordServiceImpl medicalRecordService;

    private MedicalRecord medicalRecord;

    @BeforeEach
    public void setUpPerTest() {
        List<String> medications = new ArrayList<>();
        medications.add("pharmacol:5000mg");
        List<String> allergies = new ArrayList<>();
        allergies.add("nillacilan");
        medicalRecord = MedicalRecord.builder()
                .firstName("Steve")
                .lastName("Lander")
                .birthdate("2000-01-01")
                .medications(medications)
                .allergies(allergies)
                .build();
    }

    @DisplayName("JUnit test for create medical record REST API")
    @Test
    public void givenMedicalRecordObject_whenCreateMedicalRecord_thenReturnSavedMedicalRecord() {
        Mockito.when(medicalRecordService.saveMedicalRecord(ArgumentMatchers.any())).thenReturn(medicalRecord);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            mvc.perform(MockMvcRequestBuilders.post("/medicalrecord")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(medicalRecord)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.firstName", Matchers.equalTo("Steve")))
                    .andExpect(jsonPath("$.lastName", Matchers.equalTo("Lander")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("JUnit test for update medical record REST API - positive scenario")
    @Test
    public void givenUpdatedMedicalRecord_whenUpdateMedicalRecord_thenReturnUpdatedMedicalRecordObject() {
        List<String> medications = new ArrayList<>();
        medications.add("pharmacol:5000mg");
        List<String> allergies = new ArrayList<>();
        allergies.add("nillacilan");
        MedicalRecord medicalRecord1 = MedicalRecord.builder()
                .firstName("Steve")
                .lastName("Lander")
                .birthdate("1999-08-19")
                .medications(medications)
                .allergies(allergies)
                .build();

        given(medicalRecordService.getMedicalRecordByFirstNameAndLastName(medicalRecord.getFirstName(), medicalRecord.getLastName())).willReturn(Optional.of(medicalRecord));
        //Mockito.when(medicalRecordService.updateMedicalRecord(ArgumentMatchers.any())).thenReturn(medicalRecord1);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            mvc.perform(MockMvcRequestBuilders.put("/medicalrecord")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(medicalRecord1)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstName", Matchers.equalTo("Steve")))
                    .andExpect(jsonPath("$.lastName", Matchers.equalTo("Lander")))
                    .andExpect(jsonPath("$.birthdate", Matchers.equalTo("1999-08-19")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("JUnit test for update medical record REST API - negative scenario")
    @Test
    public void givenUpdatedMedicalRecord_whenUpdateMedicalRecord_thenReturn404() {
        List<String> medications = new ArrayList<>();
        medications.add("pharmacol:5000mg");
        List<String> allergies = new ArrayList<>();
        allergies.add("nillacilan");
        MedicalRecord medicalRecord1 = MedicalRecord.builder()
                .firstName("Steve")
                .lastName("Lander")
                .birthdate("2000-01-01")
                .medications(medications)
                .allergies(allergies)
                .build();

        given(medicalRecordService.getMedicalRecordByFirstNameAndLastName(medicalRecord.getFirstName(), medicalRecord.getLastName())).willReturn(Optional.empty());
        //Mockito.when(medicalRecordService.updateMedicalRecord(ArgumentMatchers.any())).thenReturn(medicalRecord1);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            mvc.perform(MockMvcRequestBuilders.put("/medicalrecord")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(medicalRecord1)))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("JUnit test for delete medical record REST API")
    @Test
    public void givenMedicalRecordObject_whenDeleteMedicalRecord_thenReturn200() {
        try {
            mvc.perform(MockMvcRequestBuilders.delete("/medicalrecord")
                            .param("firstName", "Steve")
                            .param("lastName", "Lander"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
