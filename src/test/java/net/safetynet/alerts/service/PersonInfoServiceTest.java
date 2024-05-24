package net.safetynet.alerts.service;

import net.safetynet.alerts.dto.PersonInfoDTO;
import net.safetynet.alerts.entity.MedicalRecord;
import net.safetynet.alerts.entity.Person;
import net.safetynet.alerts.repository.MedicalRecordRepository;
import net.safetynet.alerts.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.h2.mvstore.type.ObjectDataType.serialize;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class PersonInfoServiceTest {
    @Mock
    private PersonRepository personRepository;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @InjectMocks
    private PersonInfoService personInfoService;

    private Person person;
    private MedicalRecord medicalRecord;

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

        List<String> medications = new ArrayList<>();
        medications.add("pharmacol:5000mg");
        List<String> allergies = new ArrayList<>();
        allergies.add("nillacilan");
        try {
            medicalRecord = MedicalRecord.builder()
                    .firstName("Steve")
                    .lastName("Lander")
                    .birthdate("2000-01-01")
                    .medications(new SerialBlob(serialize(medications)))
                    .allergies(new SerialBlob(serialize(allergies)))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("JUnit test for getPersonInfoByFirstNameAndLastName method")
    @Test
    public void givenFirstNameAndLastName_whenGetPersonInfoByFirstNameAndLastName_thenReturnPersonInfoDTO() {
        given(personRepository.findByFirstNameEqualsAndLastNameEquals(person.getFirstName(), person.getLastName())).willReturn(person);
        given(medicalRecordRepository.findByFirstNameEqualsAndLastNameEquals(person.getFirstName(), person.getLastName())).willReturn(medicalRecord);
        PersonInfoDTO personInfoDTO = personInfoService.getPersonInfoByFirstNameAndLastName("Steve", "Lander");
        assertThat(personInfoDTO).isNotNull();
    }
}
