package net.safetynet.alerts.controller;

import lombok.AllArgsConstructor;
import net.safetynet.alerts.dto.FireAlertDTO;
import net.safetynet.alerts.service.FireAlertService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/fire")
public class FireAlertController {
    private FireAlertService fireAlertService;

    @GetMapping
    public List<FireAlertDTO> getFireAlert(@RequestParam String address) {
        return fireAlertService.getFireStationAndPersonsByAddress(address);
    }
}
