package ru.otus.homework4.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.homework4.annotation.SpringBootTestWithTestProfile;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("PersonServiceImpl test")
@SpringBootTestWithTestProfile
class PersonServiceImplTest {

    @Autowired
    private PersonService personService;

    @DisplayName("shouldCorrectGetPerson test")
    @Test
    void shouldCorrectGetPerson() {
        assertThat(personService.getPerson("Ivan", "Ivanov"))
                .extracting("firstName", "lastName").containsExactly("Ivan", "Ivanov");
    }
}