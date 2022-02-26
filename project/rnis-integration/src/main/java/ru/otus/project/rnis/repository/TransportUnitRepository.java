package ru.otus.project.rnis.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.otus.project.rnis.entity.TransportUnit;

import java.util.List;
import java.util.Optional;

public interface TransportUnitRepository extends JpaRepository<TransportUnit, Long>, JpaSpecificationExecutor<TransportUnit> {

    @Query("select t.id from TransportUnit t")
    List<Long> findAllIds();

    @EntityGraph(attributePaths = {"transportType"})
    List<TransportUnit> findAll(Specification<TransportUnit> specification);

    @EntityGraph(attributePaths = {"municipality", "organization", "transportType"})
    Optional<TransportUnit> findFullById(Long id);

}