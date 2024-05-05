package net.safetynet.alerts.service;

import net.safetynet.alerts.entity.FireStation;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface FireStationService {
    FireStation saveFireStation(FireStation fireStation);

    List<FireStation> getFireStationByStation(String station);

    Optional<FireStation> getFireStationById(Long id);

    ResponseEntity<FireStation> updateFireStation(FireStation fireStation);

    void deleteFireStationNumber(String station);

    List<String> getPhoneNumberOfAllPersonsCoveredByStation(String station);
}
