package ru.otus.homework3.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("QuestionServiceImpl test")
@SpringBootTest
class QuestionServiceImplTest {

    @Autowired
    QuestionService questionService;

    @DisplayName("shouldCorrectGetAll test")
    @Test
    void shouldCorrectGetAll() {
        assertFalse(questionService.getAll().isEmpty());
    }
}