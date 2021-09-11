package ru.otus.homework2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework2.dao.PersonDao;
import ru.otus.homework2.domain.Person;

@RequiredArgsConstructor
@Service
public class PersonServiceImpl implements PersonService {

    private final IOService ioService;
    private final PersonDao personDao;

    @Override
    public Person getPerson() {

        ioService.write("Enter a name: ");
        String firstName = ioService.readString();
        ioService.write("Enter your last name: ");
        String lastName = ioService.readString();

        return personDao.getPerson(firstName, lastName);
    }
}