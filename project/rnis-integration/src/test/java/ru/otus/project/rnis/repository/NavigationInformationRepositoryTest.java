package ru.otus.project.rnis.repository;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import ru.otus.project.rnis.entity.*;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с навигационной информацией должен")
@DataJpaTest
class NavigationInformationRepositoryTest {

    @Autowired
    private NavigationInformationRepository navigationInformationRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private final static Long ID_1 = 1L;
    private final static Long ID_6 = 6L;
    private final static Long ID_7 = 7L;
    private final static TransportUnit TU_1 = initTU1();
    private final static NavigationInformation NI_1 = initNI1();
    private final static NavigationInformation NI_6 = initNI6();
    private final static NavigationInformation NI_7 = initNI7();
    private NavigationInformation exampleSaveNI = initExampleSaveNI();

    private static TransportUnit initTU1() {
        return new TransportUnit(ID_1,
                                 "объект1",
                                 "телефон1",
                                 Instant.parse("2022-02-02T00:56:03Z"),
                                 45.1234,
                                 45.1234,
                                 "100 км/ч",
                                 100.0,
                                 new Municipality(ID_1, "МО1"),
                                 new TransportType(ID_1, "Тип транспорта1"),
                                 new Organization(ID_1, "Организация1"));
    }

    private static NavigationInformation initNI1() {
        return new NavigationInformation(ID_1,
                                         Instant.parse("2022-02-02T00:50:03Z"),
                                         45.1234,
                                         45.1234,
                                         "100 км/ч",
                                         100.0,
                                         TU_1);
    }

    private static NavigationInformation initNI6() {
        return new NavigationInformation(ID_6,
                                         Instant.parse("2022-02-02T00:55:03Z"),
                                         45.1234,
                                         45.1234,
                                         "100 км/ч",
                                         100.0,
                                         TU_1);
    }

    private static NavigationInformation initNI7() {
        return new NavigationInformation(ID_7,
                                         Instant.parse("2022-02-02T00:56:03Z"),
                                         45.1234,
                                         45.1234,
                                         "100 км/ч",
                                         100.0,
                                         TU_1);
    }

    private static NavigationInformation initExampleSaveNI() {
        return new NavigationInformation(Instant.parse("2022-02-02T00:55:03Z"),
                                         45.1234,
                                         45.1234,
                                         "100 км/ч",
                                         100.0,
                                         TU_1);
    }

    @DisplayName("получить последнюю навигационную информацию по идентификатору транспортной единицы")
    @Test
    void shouldCorrectFindFirstByTransportUnitIdOrderByInformationDateDesc() {
        var expectedNavigationInformation = NI_7;

        var actualNavigationInformation =
                navigationInformationRepository.findFirstByTransportUnitIdOrderByIdDesc(ID_1)
                                               .orElseThrow();

        actualNavigationInformation.setTransportUnit(unProxy(actualNavigationInformation.getTransportUnit()));

        assertThat(actualNavigationInformation).usingRecursiveComparison().isEqualTo(expectedNavigationInformation);
    }

    @DisplayName("сохранять навигационную информацию")
    @Test
    void shouldCorrectSave() {
        var expectedNavigationInformation = exampleSaveNI;
        navigationInformationRepository.save(expectedNavigationInformation);
        testEntityManager.detach(expectedNavigationInformation);
        var actualNavigationInformation = testEntityManager.find(NavigationInformation.class, exampleSaveNI.getId());

        actualNavigationInformation.setTransportUnit(unProxy(actualNavigationInformation.getTransportUnit()));

        assertThat(actualNavigationInformation).usingRecursiveComparison().isEqualTo(expectedNavigationInformation);
    }

    @DisplayName("удалять список навигационной информации по идентификаторам транспортных единиц")
    @Test
    void shouldCorrectDeleteAllByTransportUnitId() {
        var deleteTransportUnitIds = List.of(ID_1);

        navigationInformationRepository.deleteAllByTransportUnitIdIn(deleteTransportUnitIds);

        var query = "select n from NavigationInformation n where n.transportUnit.id in :ids";
        var actualNavigationInformationList = testEntityManager.getEntityManager()
                                                               .createQuery(query, NavigationInformation.class)
                                                               .setParameter("ids", deleteTransportUnitIds)
                                                               .getResultList();
        assertThat(actualNavigationInformationList).isEmpty();
    }

    @DisplayName("получать постраничный список навигационной информации по идентификатору транспортной единицы")
    @Test
    void shouldCorrectFindAllByTransportUnitId() {
        var expectedNavigationInformationList = List.of(NI_7, NI_6, NI_1);

        var actualNavigationInformationList =
                navigationInformationRepository.findByTransportUnitIdOrderByIdDesc(ID_1, PageRequest.of(0, 3))
                                               .toList();
        actualNavigationInformationList.forEach(ni -> ni.setTransportUnit(unProxy(ni.getTransportUnit())));
        assertThat(actualNavigationInformationList).usingRecursiveComparison()
                                                   .isEqualTo(expectedNavigationInformationList);
    }


    private TransportUnit unProxy(TransportUnit proxyTransportUnit) {
        var transportUnit = (TransportUnit) Hibernate.unproxy(proxyTransportUnit);
        transportUnit.setTransportType((TransportType) Hibernate.unproxy(transportUnit.getTransportType()));
        transportUnit.setOrganization((Organization) Hibernate.unproxy(transportUnit.getOrganization()));
        transportUnit.setMunicipality((Municipality) Hibernate.unproxy(transportUnit.getMunicipality()));
        return transportUnit;
    }

}