package ru.otus.homework3.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.io.*;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LSIOServiceImpl test")
@SpringBootTest
class LSIOServiceImplTest {

    private final static OutputStream outContent = new ByteArrayOutputStream();
    private final static InputStream inContent = new ByteArrayInputStream(new byte[0]);

    @Autowired
    private LSIOService lsioService;

    @Autowired
    private LSService lsService;

    @TestConfiguration
    public static class InnerConfig {

        @Bean
        IOService ioService() {
            return new IOServiceImpl(outContent, inContent);
        }
    }

    @DisplayName("shouldCorrectWrite test")
    @Test
    void shouldCorrectWrite() {
        lsService.setCurrentLocale(Locale.forLanguageTag("en-EN"));
        lsioService.write("enter_first_name");
        assertEquals("Enter a name: \r\n", outContent.toString());
    }
}