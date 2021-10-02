package ru.otus.homework4.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework4.dao.PersonDao;
import ru.otus.homework4.domain.Person;

@RequiredArgsConstructor
@Service
public class PersonServiceImpl implements PersonService {

    private final LocalizationIOService localizationIOService;
    private final PersonDao personDao;

    @Override
    public Person getPerson(String firstName, String lastName) {
        if (firstName == null) {
            writeFirstName();
            firstName = localizationIOService.readString();
        }
        if (lastName == null) {
            writeLastName();
            lastName = localizationIOService.readString();
        }
        return personDao.getPerson(firstName, lastName);
    }

    private void writeFirstName() {
        localizationIOService.write("enter_first_name");
    }

    private void writeLastName() {
        localizationIOService.write("enter_last_name");
    }
}