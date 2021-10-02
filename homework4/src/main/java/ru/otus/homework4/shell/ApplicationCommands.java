package ru.otus.homework4.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework4.service.QuizService;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommands {

    private final QuizService quizService;

    @ShellMethod(key = "start", value = "Start test")
    public String startTest() {
        quizService.startTest();
        return "Test end";
    }
}