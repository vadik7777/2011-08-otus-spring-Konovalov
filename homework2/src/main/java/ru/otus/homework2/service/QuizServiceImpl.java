package ru.otus.homework2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework2.domain.Answer;
import ru.otus.homework2.domain.TestResult;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuizServiceImpl implements QuizServise {

    private final PrintService printService;
    private final QuestionService questionService;
    private final TestResultService testResultService;

    @Override
    public void startTest() {

        printService.write("Enter a name: ");
        String firstName = printService.read();
        printService.write("Enter your last name: ");
        String lastName = printService.read();

        TestResult testResult = testResultService.getTestResultByPerson(firstName, lastName);

        questionService.getAll().forEach(question -> {
            printService.write(question.getName());
            List<Answer> answers = question.getAnswers();
            answers.forEach(answer -> printService.write(((answers.indexOf(answer) + 1) + " - " + answer.getName())));
            int answerId = Integer.parseInt(printService.read());
            Answer answer = answers.get(answerId - 1);
            if (answer.isRight()) {
                testResult.setCorrectAnswers(testResult.getCorrectAnswers() + 1);
                if (testResult.getCorrectAnswers() >= testResult.getCorrectAnswersToPass() && !testResult.isPassed()) {
                    testResult.setPassed(true);
                }
            } else {
                testResult.setWrongAnswers(testResult.getWrongAnswers() + 1);
            }
        });

        printService.write(String.format("Test result: %s, correct answers - %d, incorrect answers - %d.",
                testResult.isPassed() ? "pass": "not pass",
                testResult.getCorrectAnswers(),
                testResult.getWrongAnswers()));
    }
}