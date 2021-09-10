package ru.otus.homework2.domain;

import lombok.Data;

@Data
public class TestResult {

    private final Person person;
    private final int correctAnswersToPass;
    private int correctAnswers;
    private boolean passed = false;
    private int wrongAnswers = 0;

    public boolean isPassed() {
        return correctAnswers >= correctAnswersToPass;
    }

    public void incCorrectAnswers() {
        correctAnswers++;
    }

    public void incWrongAnswers() {
        wrongAnswers++;
    }
}