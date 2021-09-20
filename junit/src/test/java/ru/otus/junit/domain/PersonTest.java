package ru.otus.junit.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.*;

@DisplayName("Класс Person")
class PersonTest {

    @DisplayName("корректно создаётся конструктором")
    @Test
    void shouldHaveCorrectConstructor() {
        Person person = new Person(42, "Ivan");

        assertEquals("Ivan", person.getName());
        assertEquals(42, person.getAge());
    }

    @DisplayName("должен")
    @Test
    void shouldHaveCorrectName() {
        Person person = new Person(42, "Ivan");
        person.setAge(44);
        person.setName("Petr");

        assertThat(person).extracting("name", "age").containsExactly("Petr", 42);
        /*assertEquals(44, person.getAge());
        assertEquals("Petr", person.getName());*/
    }

    @DisplayName("должен увеличивать возраст при вызове birthDay")
    @Test
    void shouldHaveCorrectBirthDay() {
        Person person = new Person(42, "Ivan");
        person.birthDay();

        assertEquals(43, person.getAge());
    }
}