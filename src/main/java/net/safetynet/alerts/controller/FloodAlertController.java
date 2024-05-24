package net.safetynet.alerts.controller;

import lombok.AllArgsConstructor;
import net.safetynet.alerts.dto.FloodAlertDTO;
import net.safetynet.alerts.service.FloodAlertService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/flood")
public class FloodAlertController {
    private FloodAlertService floodAlertService;

    @GetMapping("/stations")
    public List<FloodAlertDTO> getAddressAndPeopleByFloodStations(@RequestParam List<String> stations) {
        return floodAlertService.getAddressAndPeopleByFloodStations(stations);
    }
}
