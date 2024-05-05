package net.safetynet.alerts.controller;

import lombok.AllArgsConstructor;
import net.safetynet.alerts.dto.PersonInfoDTO;
import net.safetynet.alerts.service.PersonInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/personInfo")
public class PersonInfoController {
    private PersonInfoService personInfoService;

    @GetMapping
    public PersonInfoDTO getPersonInfo(@RequestParam String firstName, @RequestParam String lastName) {
        return personInfoService.getPersonInfoByFirstNameAndLastName(firstName, lastName);
    }
}
