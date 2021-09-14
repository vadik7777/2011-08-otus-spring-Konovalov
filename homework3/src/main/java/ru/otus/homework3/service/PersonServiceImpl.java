package ru.otus.homework3.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.otus.homework2.dao.PersonDao;
import ru.otus.homework2.service.IOService;

import java.util.Locale;

@Primary
@Service("personServiceHomeWork3Impl")
public class PersonServiceImpl extends ru.otus.homework2.service.PersonServiceImpl {

    private final IOService ioService;
    private final String locale;
    private final MessageSource messageSource;

    public PersonServiceImpl(IOService ioService, PersonDao personDao, MessageSource messageSource, @Value("${locale}") String locale) {
        super(ioService, personDao);
        this.ioService = ioService;
        this.messageSource = messageSource;
        this.locale = locale;
    }

    @Override
    public void writeFirstName() {
        ioService.write(messageSource.getMessage("enter_first_name", null, Locale.forLanguageTag(locale)));
    }

    @Override
    public void writeLastName() {
        ioService.write(messageSource.getMessage("enter_last_name", null, Locale.forLanguageTag(locale)));
    }
}