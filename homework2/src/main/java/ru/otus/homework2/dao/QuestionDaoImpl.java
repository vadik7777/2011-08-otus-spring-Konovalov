package ru.otus.homework2.dao;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.otus.homework2.domain.Question;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class QuestionDaoImpl implements QuestionDao {

    @Value("${questions}")
    private Resource questions;

    private List<Question> questionList;

    @PostConstruct
    public void initQuestions() throws IOException {
        CsvToBean csv = new CsvToBean();
        CSVReader csvReader = new CSVReader(new InputStreamReader(questions.getInputStream()));
        questionList = csv.parse(setColunmMapping(), csvReader);
    }

    @Override
    public List<Question> getAll() {
        return questionList;
    }

    private ColumnPositionMappingStrategy setColunmMapping()
    {
        ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
        strategy.setType(Question.class);
        String[] columns = new String[] {"id", "name"};
        strategy.setColumnMapping(columns);
        return strategy;
    }
}