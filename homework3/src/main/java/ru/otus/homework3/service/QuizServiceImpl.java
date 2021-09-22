package ru.otus.homework3.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.homework3.domain.Answer;
import ru.otus.homework3.domain.Person;
import ru.otus.homework3.domain.Question;
import ru.otus.homework3.domain.TestResult;

import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {

    private final LSIOService lsioService;
    private final IOService ioService;
    private final QuestionService questionService;
    private final PersonService personService;
    private final int correctAnswers;
    private final LSService lsService;

    public QuizServiceImpl(LSIOService lsioService, QuestionService questionService, PersonService personService,
                           @Value("${correctAnswersToPass}") int correctAnswers, IOService ioService, LSService lsService) {
        this.lsioService = lsioService;
        this.questionService = questionService;
        this.personService = personService;
        this.correctAnswers = correctAnswers;
        this.ioService = ioService;
        this.lsService = lsService;

    }

    @Override
    public void startTest() {

        Person person = personService.getPerson(null, null);
        TestResult testResult = new TestResult(person, correctAnswers);

        questionService.getAll().forEach(question -> {
            boolean answerResult = checkAnswer(question);
            if (answerResult) {
                testResult.incCorrectAnswers();
            } else {
                testResult.incWrongAnswers();
            }
        });
        writeResult(testResult.isPassed(), testResult.getCorrectAnswers(), testResult.getWrongAnswers());
    }

    private boolean checkAnswer(Question question) {
        writeQuestion(question.getName());
        List<Answer> answers = question.getAnswers();
        for (int i = 0; i < answers.size(); i++) {
            writeAnswer(i + 1, answers.get(i).getName());
        }
        int answerId = readAnswer();
        Answer answer = answers.get(answerId - 1);
        return answer.isRight();
    }

    private void writeAnswer(int answerIndex, String answer) {
        lsioService.write("answer", answerIndex, answer);
    }

    private void writeQuestion(String question) {
        lsioService.write("question", question);
    }

    private void writeResult(boolean pass, int correctAnswers, int wrongAnswers) {
        lsioService.write("result", lsService.getMessage(pass ? "pass": "not_pass"),
                correctAnswers, wrongAnswers);
    }

    private int readAnswer() {
        return ioService.readInt();
    }
}