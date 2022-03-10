package ru.otus.project.rnis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.project.rnis.entity.TransportType;

import java.util.List;

public interface TransportTypeRepository extends JpaRepository<TransportType, Long> {

    @Query("select t.id from TransportType t")
    List<Long> findAllIds();

}