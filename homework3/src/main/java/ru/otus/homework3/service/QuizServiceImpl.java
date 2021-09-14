package ru.otus.homework3.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.otus.homework2.domain.Answer;
import ru.otus.homework2.domain.Question;
import ru.otus.homework2.service.IOService;
import ru.otus.homework2.service.PersonService;
import ru.otus.homework2.service.QuestionService;

import java.util.List;
import java.util.Locale;

@Primary
@Service("quizServiceHomeWork3Impl")
public class QuizServiceImpl extends ru.otus.homework2.service.QuizServiceImpl {

    private final MessageSource messageSource;
    private final IOService ioService;
    private final String locale;

    public QuizServiceImpl(IOService ioService, QuestionService questionService, PersonService personService,
                           @Value("${correctAnswersToPass}") int correctAnswers, MessageSource messageSource,
                           @Value("${locale}") String locale) {
        super(ioService, questionService, personService, correctAnswers);
        this.messageSource = messageSource;
        this.locale = locale;
        this.ioService = ioService;
    }

    @Override
    public boolean checkAnswer(Question question) {
        writeQuestion(question.getName());
        List<Answer> answers = question.getAnswers();
        for (int i = 0; i < answers.size(); i++) {
            writeAnswer(i + 1, question.getName() + "." + answers.get(i).getName());
        }
        int answerId = super.readAnswer();
        Answer answer = answers.get(answerId - 1);
        return answer.isRight();
    }

    @Override
    public void writeAnswer(int index, String answer) {
        answer = messageSource.getMessage(answer, null, Locale.forLanguageTag(locale));
        answer = messageSource.getMessage("answer", new String[] {String.valueOf(index), answer}, Locale.forLanguageTag(locale));
        ioService.write(answer);
    }

    @Override
    public void writeQuestion(String question) {
        question = messageSource.getMessage(question, null, Locale.forLanguageTag(locale));
        ioService.write(question);
    }

    @Override
    public void writeResult(boolean pass, int correctAnswers, int wrongAnswers) {
        String testResult = messageSource.getMessage(pass ? "pass" : "not_pass", null,
                Locale.forLanguageTag(locale));
        String result = messageSource.getMessage("result",
                new String[] {testResult, String.valueOf(correctAnswers), String.valueOf(wrongAnswers)},
                Locale.forLanguageTag(locale));
        ioService.write(result);
    }
}