package net.safetynet.alerts.service;

import net.safetynet.alerts.dto.ChildAlertDTO;
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
public class ChildAlertServiceTest {
    @Mock
    private PersonRepository personRepository;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @InjectMocks
    private ChildAlertService childAlertService;

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

    @DisplayName("JUnit test for getAllChildLivingAtAddress method")
    @Test
    public void givenAddressWithNoChild_whenGetAllChildLivingAtAddress_thenReturnEmptyList() {
        List<Person> persons = new ArrayList<>();
        persons.add(person);

        given(personRepository.findAllByAddress(person.getAddress())).willReturn(persons);
        given(medicalRecordRepository.findByFirstNameEqualsAndLastNameEquals(person.getFirstName(), person.getLastName())).willReturn(medicalRecord);

        List<ChildAlertDTO> childAlertDTOList = childAlertService.getAllChildLivingAtAddress("123 Donald Avenue");

        assertThat(childAlertDTOList).isEmpty();
    }

    @DisplayName("JUnit test for getAllChildLivingAtAddress method")
    @Test
    public void givenAddressWithChild_whenGetAllChildLivingAtAddress_thenReturnChildAlertDTOList() {
        List<Person> persons = new ArrayList<>();
        persons.add(person);
        medicalRecord.setBirthdate("2012-01-01");

        given(personRepository.findAllByAddress(person.getAddress())).willReturn(persons);
        given(medicalRecordRepository.findByFirstNameEqualsAndLastNameEquals(person.getFirstName(), person.getLastName())).willReturn(medicalRecord);

        List<ChildAlertDTO> childAlertDTOList = childAlertService.getAllChildLivingAtAddress("123 Donald Avenue");

        assertThat(childAlertDTOList).isNotEmpty();
    }
}
