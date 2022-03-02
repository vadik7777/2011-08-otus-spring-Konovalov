package ru.otus.project.rnis.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.project.rnis.entity.NavigationInformation;

import java.util.List;
import java.util.Optional;

public interface NavigationInformationRepository extends JpaRepository<NavigationInformation, Long> {

    void deleteAllByTransportUnitIdIn(List<Long> transportUnitIds);

    Optional<NavigationInformation> findFirstByTransportUnitIdOrderByIdDesc(Long id);

    Page<NavigationInformation> findByTransportUnitId(Long id, Pageable pageable);

}