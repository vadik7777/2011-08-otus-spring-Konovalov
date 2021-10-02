package ru.otus.homework4.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("IOServiceImpl test")
class IOServiceImplTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayInputStream inContent = new ByteArrayInputStream("1".getBytes());

    private final IOService ioService = new IOServiceImpl(outContent, inContent);

    @DisplayName("shouldCorrectWrite test")
    @Test
    void shouldCorrectWrite() {
        ioService.write("some string");
        assertEquals("some string\r\n", outContent.toString());
    }

    @DisplayName("shouldCorrectReadString test")
    @Test
    void shouldCorrectReadString() {
        assertEquals(ioService.readString(), "1");
    }

    @DisplayName("shouldCorrectReadInt test")
    @Test
    void shouldCorrectReadInt() {
        assertEquals(ioService.readInt(), 1);
    }
}