package ru.otus.homework4.dao;

import au.com.bytecode.opencsv.CSVReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.homework4.config.AppConfig;
import ru.otus.homework4.domain.Answer;
import ru.otus.homework4.domain.Question;
import ru.otus.homework4.exception.CSVReadException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class QuestionDaoImpl implements QuestionDao {

    private final static String BOOLEAN_STRING_TRUE = "true";

    private final AppConfig appConfig;

    public List<Question> getAll() {
        return readQuestions();
    }

    private List<Question> readQuestions()  {
        List<Question> questionsList = new ArrayList<>();
        List<String[]> allRows = new ArrayList<>();
        try (InputStreamReader inputStreamReader = new InputStreamReader(getClass().getClassLoader()
                .getResourceAsStream(appConfig.getFileName()));
             CSVReader csvReader = new CSVReader(inputStreamReader)) {
            allRows.addAll(csvReader.readAll());
        } catch (IOException ioException) {
            throw new CSVReadException(ioException);
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