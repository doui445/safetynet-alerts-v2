package net.safetynet.alerts.controller;

import lombok.AllArgsConstructor;
import net.safetynet.alerts.service.FireStationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/phonealert")
public class PhoneAlertController {
    private FireStationService fireStationService;

    @GetMapping
    public List<String> getPhoneOfPeopleByFirestation(@RequestParam String station) {
        return fireStationService.getPhoneNumberOfAllPersonsCoveredByStation(station);
    }
}
