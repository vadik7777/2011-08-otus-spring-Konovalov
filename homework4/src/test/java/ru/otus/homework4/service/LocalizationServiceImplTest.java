package ru.otus.homework4.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.homework4.annotation.SpringBootTestWithTestProfile;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LocalizationServiceImpl test")
@SpringBootTestWithTestProfile
class LocalizationServiceImplTest {

    @Autowired
    private LocalizationService localizationService;

    @DisplayName("shouldCorrectGetMessageWithSetCurrentLocale test")
    @Test
    void shouldCorrectGetMessageWithSetCurrentLocale() {
        assertEquals(localizationService.getMessage("enter_first_name"), "Enter a name: ");
    }
}