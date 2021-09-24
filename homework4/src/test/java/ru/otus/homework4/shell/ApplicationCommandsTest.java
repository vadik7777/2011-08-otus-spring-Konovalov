package ru.otus.homework4.shell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.Shell;
import ru.otus.homework4.annotation.SpringBootTestWithTestProfile;
import ru.otus.homework4.service.QuizService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;

@DisplayName("ApplicationCommands test")
@SpringBootTestWithTestProfile
class ApplicationCommandsTest {

    @MockBean
    private QuizService quizService;

    @Autowired
    private Shell shell;

    @DisplayName("shouldCorrectStart test")
    @Test
    void shouldCorrectStartTest() {
        doNothing().when(quizService).startTest();
        String res = (String) shell.evaluate(() -> "start");
        assertEquals("Test end", res);
    }

}