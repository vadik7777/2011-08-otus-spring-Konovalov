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
        writeFirstName();
        String firstName = ioService.readString();
        writeLastName();
        String lastName = ioService.readString();
        return personDao.getPerson(firstName, lastName);
    }

    @Override
    public void writeFirstName() {
        ioService.write("Enter a name: ");
    }

    @Override
    public void writeLastName() {
        ioService.write("Enter your last name: ");
    }
}