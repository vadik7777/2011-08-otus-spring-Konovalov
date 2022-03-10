package ru.otus.project.rnis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.project.rnis.entity.Municipality;

import java.util.List;

public interface MunicipalityRepository extends JpaRepository<Municipality, Long> {

    @Query("select m.id from Municipality m")
    List<Long> findAllIds();

}