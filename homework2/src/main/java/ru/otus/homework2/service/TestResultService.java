package ru.otus.homework2.service;

import ru.otus.homework2.domain.TestResult;

public interface TestResultService {

    TestResult getTestResultByPerson(String firstName, String lastName);

}