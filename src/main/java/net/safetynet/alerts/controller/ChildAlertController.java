package net.safetynet.alerts.controller;

import lombok.AllArgsConstructor;
import net.safetynet.alerts.dto.ChildAlertDTO;
import net.safetynet.alerts.service.ChildAlertService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/childAlert")
public class ChildAlertController {
    private ChildAlertService childAlertService;

    @GetMapping
    public List<ChildAlertDTO> getChildAlert(@RequestParam String address) {
        return childAlertService.getAllChildLivingAtAddress(address);
    }
}
