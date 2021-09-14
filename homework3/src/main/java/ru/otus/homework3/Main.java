package ru.otus.homework3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.homework2.service.QuizService;

@SpringBootApplication(scanBasePackages = {"ru.otus.homework2.*", "ru.otus.homework3.*"})
public class Main {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class, args);
        QuizService quizService = context.getBean(QuizService.class);
        quizService.startTest();
    }
}