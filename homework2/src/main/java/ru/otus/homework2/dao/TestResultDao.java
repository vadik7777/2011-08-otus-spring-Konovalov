package ru.otus.homework2.dao;

import ru.otus.homework2.domain.TestResult;

public interface TestResultDao {

    TestResult getTestResultByPerson(String firstName, String lastName);

}