package ru.otus.homework2.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.homework2.domain.Answer;
import ru.otus.homework2.domain.Person;
import ru.otus.homework2.domain.Question;
import ru.otus.homework2.domain.TestResult;

import java.util.List;

@Service
public class QuizServiceImpl implements QuizServise {

    private final IOService ioService;
    private final QuestionService questionService;
    private final PersonService personService;
    private final int correctAnswers;

    public QuizServiceImpl(IOService ioService, QuestionService questionService, PersonService personService,
                           @Value("${correctAnswersToPass}") int correctAnswers) {
        this.ioService = ioService;
        this.questionService = questionService;
        this.personService = personService;
        this.correctAnswers = correctAnswers;
    }

    @Override
    public void startTest() {

        Person person = personService.getPerson();
        TestResult testResult = new TestResult(person, correctAnswers);

        questionService.getAll().forEach(question -> {
            boolean answerResult = writeQuestionAndCheckAnswer(question);
            if (answerResult) {
                testResult.incCorrectAnswers();
            } else {
                testResult.incWrongAnswers();
            }
        });
        writeResult(testResult);
    }

    private boolean writeQuestionAndCheckAnswer(Question question) {
        ioService.write(question.getName());
        List<Answer> answers = question.getAnswers();
        for (int i = 0; i < answers.size(); i++) {
            ioService.write(i + 1 + " - " + answers.get(i).getName());
        }
        int answerId = ioService.readInt();
        Answer answer = answers.get(answerId - 1);
        return answer.isRight();
    }

    private void writeResult(TestResult testResult) {
        ioService.write(String.format("Test result: %s, correct answers - %d, incorrect answers - %d.",
                testResult.isPassed() ? "pass": "not pass",
                testResult.getCorrectAnswers(),
                testResult.getWrongAnswers()));
    }
}