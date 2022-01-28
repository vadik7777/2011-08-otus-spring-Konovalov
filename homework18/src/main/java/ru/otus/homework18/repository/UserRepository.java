package ru.otus.homework18.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework18.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);

}
