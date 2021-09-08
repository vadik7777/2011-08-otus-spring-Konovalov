package ru.otus.homework2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework2.dao.TestResultDao;
import ru.otus.homework2.domain.TestResult;

@RequiredArgsConstructor
@Service
public class TestResultServiceImpl implements TestResultService {

    private final TestResultDao testResultDao;

    @Override
    public TestResult getTestResultByPerson(String firstName, String lastName) {
        return testResultDao.getTestResultByPerson(firstName, lastName);
    }
}