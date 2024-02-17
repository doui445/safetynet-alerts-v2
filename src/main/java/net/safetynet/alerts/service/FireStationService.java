package net.safetynet.alerts.service;

import net.safetynet.alerts.entity.FireStation;

import java.util.List;
import java.util.Optional;

public interface FireStationService {
    FireStation saveFireStation(FireStation fireStation);

    Optional<FireStation> getFireStationByStation(String station);

    FireStation updateFireStation(FireStation fireStation);

    void deleteFireStation(String station);

    List<String> getPhoneNumberOfAllPersonsCoveredByStation(String station);
}
