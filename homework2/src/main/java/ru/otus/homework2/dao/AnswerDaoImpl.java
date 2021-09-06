package ru.otus.homework2.dao;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.otus.homework2.domain.Answer;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnswerDaoImpl implements AnswerDao {

    private List<Answer> answerList;

    @Value("${answers}")
    private Resource answers;

    @PostConstruct
    public void initQuestions() throws IOException {
        CsvToBean csv = new CsvToBean();

        CSVReader csvReader = new CSVReader(new InputStreamReader(answers.getInputStream()));
        answerList = csv.parse(setColunmMapping(), csvReader);
    }

    @Override
    public List<Answer> getAllByIdQuestion(Long questionId) {
        return answerList.stream().filter(answer -> questionId.equals(answer.getQuestionId())).collect(Collectors.toList());
    }


    private ColumnPositionMappingStrategy setColunmMapping()
    {
        ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
        strategy.setType(Answer.class);
        String[] columns = new String[] {"id", "questionId", "name", "right"};
        strategy.setColumnMapping(columns);
        return strategy;
    }
}
