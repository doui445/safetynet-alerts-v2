package net.safetynet.alerts.repository;

import net.safetynet.alerts.entity.FireStation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class FireStationRepositoryTest {
    @Autowired
    FireStationRepository fireStationRepository;

    private FireStation fireStation;

    @BeforeEach
    public void setUp() {
        fireStation = FireStation.builder()
                .address("123 Donald Avenue")
                .station("10")
                .build();
    }

    @DisplayName("JUnit test for save fire station operation")
    @Test
    public void givenFireStationObject_whenSave_thenReturnSavedFireStation() {
        FireStation fireStation = FireStation.builder()
                .address("123 Donald Avenue")
                .station("10")
                .build();
        FireStation savedFireStation = fireStationRepository.save(fireStation);
        assertThat(savedFireStation).isNotNull();
        assertThat(savedFireStation.getId()).isGreaterThan(0);
    }

    @DisplayName("JUnit test for get all fire stations operation")
    @Test
    public void givenFireStationsList_whenFindAll_thenFireStationsList() {
        List<FireStation> oldFireStationList = fireStationRepository.findAll();
        FireStation fireStation1 = FireStation.builder()
                .address("225 Donald Road")
                .station("11")
                .build();
        fireStationRepository.save(fireStation);
        fireStationRepository.save(fireStation1);

        List<FireStation> newFireStationList = fireStationRepository.findAll();
        newFireStationList.removeAll(oldFireStationList);

        assertThat(newFireStationList).isNotNull();
        assertThat(newFireStationList.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for get fire station by id operation")
    @Test
    public void givenFireStationObject_whenFindById_thenReturnFireStationObject() {
        fireStationRepository.save(fireStation);
        Optional<FireStation> optionalFireStation = fireStationRepository.findById(fireStation.getId());
        assertThat(optionalFireStation.isPresent()).isTrue();
        FireStation fireStationDB = optionalFireStation.get();
        assertThat(fireStationDB).isNotNull();
    }

    @DisplayName("JUnit test for update fire station operation")
    @Test
    public void givenFireStationObject_whenUpdateFireStation_thenReturnUpdatedFireStation() {
        fireStationRepository.save(fireStation);

        Optional<FireStation> optionalFireStation = fireStationRepository.findById(fireStation.getId());
        assertThat(optionalFireStation.isPresent()).isTrue();
        FireStation savedFireStation = optionalFireStation.get();
        savedFireStation.setAddress("225 Donald Road");
        savedFireStation.setStation("20");
        FireStation updatedFireStation =  fireStationRepository.save(savedFireStation);


        assertThat(updatedFireStation.getAddress()).isEqualTo("225 Donald Road");
        assertThat(updatedFireStation.getStation()).isEqualTo("20");
    }

    @DisplayName("JUnit test for delete fire station operation")
    @Test
    public void givenFireStationObject_whenDelete_thenRemoveFireStation() {

        fireStationRepository.save(fireStation);

        fireStationRepository.deleteById(fireStation.getId());
        Optional<FireStation> optionalFireStation = fireStationRepository.findById(fireStation.getId());

        assertThat(optionalFireStation).isEmpty();
    }
}
