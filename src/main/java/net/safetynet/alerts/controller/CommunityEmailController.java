package net.safetynet.alerts.controller;

import lombok.AllArgsConstructor;
import net.safetynet.alerts.service.PersonServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/communityEmail")
public class CommunityEmailController {
    private PersonServiceImpl personService;

    @GetMapping
    public List<String> getCommunityEmail(@RequestParam String city) {
        return personService.getEmailForAllPersonsInCity(city);
    }
}
