package net.safetynet.alerts.controller;

import net.safetynet.alerts.entity.Person;
import net.safetynet.alerts.service.PersonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CommunityEmailControllerTest {
    @Mock
    private PersonServiceImpl personService;

    @InjectMocks
    private CommunityEmailController communityEmailController;

    private Person person;

    @BeforeEach
    public void SetUp() {
        person = Person.builder()
                .firstName("Steve")
                .lastName("Lander")
                .address("123 Donald Avenue")
                .city("Culver")
                .zip("97451")
                .phone("0123456789")
                .email("steve.lander@gmail.com")
                .build();
    }

    @DisplayName("JUnit test for getCommunityEmail method")
    @Test
    public void givenCity_whenGetCommunityEmail_thenReturnListOfEmails() {
        List<String> emailList = new ArrayList<>();
        emailList.add(person.getEmail());

        given(personService.getEmailForAllPersonsInCity(person.getCity())).willReturn(emailList);

        List<String> returnedEmailList = communityEmailController.getCommunityEmail(person.getCity());
        assertThat(returnedEmailList).isEqualTo(emailList);
    }
}
