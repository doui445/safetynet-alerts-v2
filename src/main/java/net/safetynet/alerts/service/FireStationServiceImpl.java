package net.safetynet.alerts.service;

import lombok.AllArgsConstructor;
import net.safetynet.alerts.entity.FireStation;
import net.safetynet.alerts.repository.FireStationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class FireStationServiceImpl implements FireStationService {
    private FireStationRepository fireStationRepository;

    @Override
    public List<String> getPhoneNumberOfAllPersonsCoveredByStation(String station) {
        return fireStationRepository.findPhoneNumberByStation(station);
    }

    @Override
    public FireStation saveFireStation(FireStation fireStation) {
        List<FireStation> fireStations = fireStationRepository.findByStation(fireStation.getStation());
        for (FireStation savedFireStation: fireStations) {
            System.out.println("FireStation already exist with given address:" + savedFireStation.getAddress());
            return null;
        }
        return fireStationRepository.save(fireStation);
    }

    @Override
    public List<FireStation> getFireStationByStation(String station) {
        return fireStationRepository.findByStation(station);
    }

    @Override
    public Optional<FireStation> getFireStationById(Long id) {
        return fireStationRepository.findById(id);
    }

    @Override
    public ResponseEntity<FireStation> updateFireStation(FireStation fireStation) {
        return getFireStationById(fireStation.getId())
                .map(savedFireStation -> {
                    savedFireStation.setAddress(fireStation.getAddress());

                    FireStation updatedFireStation = fireStationRepository.save(savedFireStation);
                    return new ResponseEntity<>(updatedFireStation, HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public void deleteFireStationNumber(String station) {
        fireStationRepository.deleteByStationEquals(station);
    }
}
