package ru.otus.project.rnis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.project.rnis.entity.Organization;

import java.util.List;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    @Query("select o.id from Organization o")
    List<Long> findAllIds();

}