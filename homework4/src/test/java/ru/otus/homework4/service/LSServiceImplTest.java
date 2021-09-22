package ru.otus.homework4.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LSServiceImpl test")
@SpringBootTest
class LSServiceImplTest {

    @Autowired
    LSService lsService;

    @DisplayName("shouldCorrectGetMessageWithSetCurrentLocale test")
    @Test
    void shouldCorrectGetMessageWithSetCurrentLocale() {
        lsService.setCurrentLocale(Locale.forLanguageTag("en-EN"));
        assertEquals(lsService.getMessage("enter_first_name"), "Enter a name: ");
    }
}