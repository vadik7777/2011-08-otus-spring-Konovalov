package ru.otus.homework4.dao;

import ru.otus.homework4.domain.Person;

public interface PersonDao {

    Person getPerson(String firstName, String lastName);
}