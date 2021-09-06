package ru.otus.homework2;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import ru.otus.homework2.domain.Answer;
import ru.otus.homework2.domain.Person;
import ru.otus.homework2.service.AnswerService;
import ru.otus.homework2.service.PersonService;
import ru.otus.homework2.service.QuestionService;

import java.util.List;
import java.util.Scanner;

@PropertySource("classpath:application.properties")
@ComponentScan
public class Main {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        PersonService personService = context.getBean(PersonService.class);
        QuestionService questionService = context.getBean(QuestionService.class);
        AnswerService answerService = context.getBean(AnswerService.class);

        int correctAnswers = Integer.parseInt(context.getEnvironment().getProperty("correctAnswers"));

        try (Scanner in = new Scanner(System.in)) {
            System.out.print("Enter a name: ");
            String firstName = in.nextLine();
            System.out.print("Enter your last name: ");
            String lastName = in.nextLine();

            Person person = personService.getPerson();
            person.setFirstName(firstName);
            person.setLastName(lastName);

            questionService.getAll().forEach(question -> {
                System.out.println(question.getName());
                List<Answer> answers = answerService.getAllByIdQuestion(question.getId());
                answers.forEach(answer -> System.out.println(((answers.indexOf(answer) + 1) + " - " + answer.getName())));
                Integer answerId = Integer.parseInt(in.nextLine());
                Answer answer = answers.get(answerId - 1);
                if (answer.getRight()) {
                    person.setCorrectAnswers(person.getCorrectAnswers() + 1);
                    if (person.getCorrectAnswers() >= correctAnswers && !person.getPassed()) {
                        person.setPassed(true);
                    }
                } else {
                    person.setWrongAnswers(person.getWrongAnswers() + 1);
                }
            });
            System.out.println(String.format("Test result: %s, correct answers - %d, incorrect answers - %d.",
                            person.getPassed() ? "pass": "not pass",
                            person.getCorrectAnswers(),
                            person.getWrongAnswers()));
        }
    }
}