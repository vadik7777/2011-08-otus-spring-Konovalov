package ru.otus.homework2.dao;

import au.com.bytecode.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.otus.homework2.domain.Answer;
import ru.otus.homework2.domain.Question;
import ru.otus.homework2.exception.CSVReadException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionDaoImpl implements QuestionDao {

    private final static String BOOLEAN_STRING_TRUE = "true";
    private final Resource questions;

    public QuestionDaoImpl(@Value("${questions}") Resource questions) {
        this.questions = questions;
    }

    public List<Question> getAll() {
        return readQuestions();
    }

    private List<Question> readQuestions()  {
        List<Question> questionsList = new ArrayList<>();
        List<String[]> allRows = new ArrayList<>();
        try (InputStreamReader inputStreamReader = new InputStreamReader(questions.getInputStream());
             CSVReader csvReader = new CSVReader(inputStreamReader)) {
            allRows.addAll(csvReader.readAll());
        } catch (IOException e) {
            throw new CSVReadException("CSV file read error");
        }
        allRows.forEach(row -> {
            String question = row[0];
            List<Answer> answerList = new ArrayList<>();
            for (int i = 1; i < row.length - 1; i = i + 2) {
                Answer answer = new Answer(row[i], parseBoolean(row[i + 1]));
                answerList.add(answer);
            }
            questionsList.add(new Question(question, answerList));
        });

        return questionsList;
    }

    private boolean parseBoolean(String row) {
        return BOOLEAN_STRING_TRUE.equals(row);
    }
}