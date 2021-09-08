package ru.otus.homework2.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.homework2.domain.Person;
import ru.otus.homework2.domain.TestResult;

@Component
public class TestResultDaoImpl implements TestResultDao {

    private final int correctAnswersToPass;

    public TestResultDaoImpl(@Value("${correctAnswersToPass}") int correctAnswersToPass) {
        this.correctAnswersToPass = correctAnswersToPass;
    }

    @Override
    public TestResult getTestResultByPerson(String firstName, String lastName) {
        Person person = new Person(firstName, lastName);
        TestResult testResult = new TestResult(person, correctAnswersToPass);
        return testResult;
    }
}