package ru.otus.homework2.service;

import ru.otus.homework2.domain.Question;

public interface QuizService {

    void startTest();

    boolean checkAnswer(Question question);

    void writeQuestion(String question);

    void writeAnswer(int index, String answer);

    void writeResult(boolean pass, int correctAnswers, int wrongAnswers);

    int readAnswer();

}