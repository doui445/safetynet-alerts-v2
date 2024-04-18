package net.safetynet.alerts.controller;

import lombok.AllArgsConstructor;
import net.safetynet.alerts.entity.FireStation;
import net.safetynet.alerts.service.FireStationServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/firestation")
public class FireStationController {
    private FireStationServiceImpl fireStationService;

    @GetMapping
    public Optional<FireStation> getFirestation(@RequestBody FireStation fireStation) {
        return fireStationService.getFireStationByStation(fireStation.getStation());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FireStation createFireStation(@RequestBody FireStation fireStation) {
        return fireStationService.saveFireStation(fireStation);
    }

    @PutMapping
    public ResponseEntity<FireStation> updateFireStation(@RequestBody FireStation fireStation) {
        return fireStationService.updateFireStation(fireStation);
    }

    @DeleteMapping("{station}")
    public ResponseEntity<String> deleteFireStation(@PathVariable("station") String station) {
        fireStationService.deleteFireStation(station);
        return ResponseEntity.status(HttpStatus.OK).body("Fire station " + station + " deleted successfully!");
    }
}
