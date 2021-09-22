package ru.otus.homework3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.homework3.service.QuizService;

import java.util.Locale;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        Locale.setDefault(Locale.forLanguageTag("ru-RU"));
        ApplicationContext context = SpringApplication.run(Main.class, args);
        QuizService quizService = context.getBean(QuizService.class);
        quizService.startTest();
    }
}