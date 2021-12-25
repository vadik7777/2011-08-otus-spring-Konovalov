package ru.otus.homework15.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework15.domain.Matryoshka;

public interface MatryoshkaRepository extends JpaRepository<Matryoshka, Long> {
}