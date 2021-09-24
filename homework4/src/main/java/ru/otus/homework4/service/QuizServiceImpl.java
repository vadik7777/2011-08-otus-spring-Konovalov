package ru.otus.homework4.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.homework4.domain.Answer;
import ru.otus.homework4.domain.Person;
import ru.otus.homework4.domain.Question;
import ru.otus.homework4.domain.TestResult;

import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {

    private final LocalizationIOService localizationIOService;
    private final QuestionService questionService;
    private final PersonService personService;
    private final int correctAnswers;

    public QuizServiceImpl(LocalizationIOService localizationIOService, QuestionService questionService, PersonService personService,
                           @Value("${application.correctAnswersToPass}") int correctAnswers) {
        this.localizationIOService = localizationIOService;
        this.questionService = questionService;
        this.personService = personService;
        this.correctAnswers = correctAnswers;

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
        localizationIOService.write("answer", answerIndex, answer);
    }

    private void writeQuestion(String question) {
        localizationIOService.write("question", question);
    }

    private void writeResult(boolean pass, int correctAnswers, int wrongAnswers) {
        localizationIOService.write(pass ? "correct-result" : "incorrect-result", correctAnswers, wrongAnswers);
    }

    private int readAnswer() {
        return localizationIOService.readInt();
    }
}