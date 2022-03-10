package ru.otus.project.rnis.repository;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.project.rnis.entity.Municipality;
import ru.otus.project.rnis.entity.Organization;
import ru.otus.project.rnis.entity.TransportType;
import ru.otus.project.rnis.entity.TransportUnit;
import ru.otus.project.rnis.model.TransportUnitFilter;
import ru.otus.project.rnis.specification.TransportUnitSpecification;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с транспортной единицей должен")
@DataJpaTest
class TransportUnitRepositoryTest {

    @Autowired
    private TransportUnitRepository transportUnitRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private final static Long ID_1 = 1L;
    private final static Long ID_2 = 2L;
    private final static Long ID_3 = 3L;
    private final static Long ID_4 = 4L;
    private final static Long ID_5 = 5L;
    private final static TransportUnit TU_1 = initTU1();
    private final static TransportUnit TU_2 = initTU2();

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

    private static TransportUnit initTU2() {
        return new TransportUnit(ID_2,
                                 "объект2",
                                 "телефон2",
                                 Instant.parse("2022-02-02T00:51:03Z"),
                                 45.1234,
                                 45.1234,
                                 "100 км/ч",
                                 100.0,
                                 new Municipality(ID_2, "МО2"),
                                 new TransportType(ID_2, "Тип транспорта2"),
                                 new Organization(ID_2, "Организация2"));
    }

    @DisplayName("сохранять список транспортных единиц")
    @Test
    void shouldCorrectSaveAll() {
        var expectedTransportUnitList = List.of(TU_1, TU_2);

        transportUnitRepository.saveAll(expectedTransportUnitList);

        expectedTransportUnitList.forEach(transportUnit -> testEntityManager.detach(transportUnit));

        var actualTransportUnit1 = unProxy(testEntityManager.find(TransportUnit.class, ID_1));
        var actualTransportUnit2 = unProxy(testEntityManager.find(TransportUnit.class, ID_2));
        var actualTransportUnitList = List.of(actualTransportUnit1, actualTransportUnit2);

        assertThat(actualTransportUnitList).usingRecursiveComparison().isEqualTo(expectedTransportUnitList);
    }

    @DisplayName("обновлять транспортную единицу")
    @Test
    void shouldCorrectUpdate() {

        var expectedTransportUnit = testEntityManager.find(TransportUnit.class, ID_1);
        expectedTransportUnit.setObjectName("проверка");

        testEntityManager.detach(expectedTransportUnit);
        transportUnitRepository.save(expectedTransportUnit);

        var actualTransportUnit = testEntityManager.find(TransportUnit.class, ID_1);

        assertThat(actualTransportUnit).usingRecursiveComparison().isEqualTo(expectedTransportUnit);
    }

    @DisplayName("получать список идентификаторов транспортных единиц")
    @Test
    void shouldCorrectFindAllIds() {
        var expectedIds = List.of(ID_1, ID_2, ID_3, ID_4, ID_5);
        var actualIds = transportUnitRepository.findAllIds();
        assertThat(actualIds).isEqualTo(expectedIds);
    }

    @DisplayName("удалять список транспортных единиц по их идентификаторам")
    @Test
    void shouldCorrectDeleteAllByIdInBatch() {
        var deleteIds = List.of(ID_1, ID_2);

        transportUnitRepository.deleteAllByIdInBatch(deleteIds);

        var query = "select n from TransportUnit n where n.id in :ids";
        var actualTransportUnitList = testEntityManager.getEntityManager()
                                                       .createQuery(query, TransportUnit.class)
                                                       .setParameter("ids", deleteIds)
                                                       .getResultList();
        assertThat(actualTransportUnitList).isEmpty();
    }

    @DisplayName("получать транспортную единицу по идентификатору")
    @Test
    void shouldCorrectFindById() {
        var expectedTransportUnit = TU_1;

        var actualTransportUnit = transportUnitRepository.findFullById(ID_1);

        assertThat(actualTransportUnit).isPresent()
                                       .get()
                                       .usingRecursiveComparison()
                                       .isEqualTo(expectedTransportUnit);
    }

    @DisplayName("получать все транспортные единицы")
    @Test
    void shouldCorrectFindAll() {
        var transportUnitFilter = new TransportUnitFilter();
        var spec = TransportUnitSpecification.transportUnitFilterBy(transportUnitFilter);
        var actualTransportUnitList = transportUnitRepository.findAll(spec);
        assertThat(actualTransportUnitList).hasSize(5);
    }

    @DisplayName("получать все транспортные единицы по идентификатору МО")
    @Test
    void shouldCorrectFindAllByMunicipality() {
        var transportUnitFilter = new TransportUnitFilter();
        transportUnitFilter.setMunicipalityId(ID_1);
        var spec = TransportUnitSpecification.transportUnitFilterBy(transportUnitFilter);
        var actualTransportUnitList = transportUnitRepository.findAll(spec);
        assertThat(actualTransportUnitList).hasSize(3);
    }

    @DisplayName("получать все транспортные единицы по идентификатору транспортного типа")
    @Test
    void shouldCorrectFindAllByTransportType() {
        var transportUnitFilter = new TransportUnitFilter();
        transportUnitFilter.setTransportTypeId(ID_1);
        var spec = TransportUnitSpecification.transportUnitFilterBy(transportUnitFilter);
        var actualTransportUnitList = transportUnitRepository.findAll(spec);
        assertThat(actualTransportUnitList).hasSize(3);
    }

    @DisplayName("получать все транспортные единицы включительно и более заданной даты получения информации")
    @Test
    void shouldCorrectFindAllByInformationDateFrom() {
        var transportUnitFilter = new TransportUnitFilter();
        var instant = Instant.parse("2022-02-02T00:53:03Z");
        transportUnitFilter.setInformationDateFrom(instant);
        var spec = TransportUnitSpecification.transportUnitFilterBy(transportUnitFilter);
        var actualTransportUnitList = transportUnitRepository.findAll(spec);
        assertThat(actualTransportUnitList).hasSize(3);
    }

    @DisplayName("копировать свойства транспортной единицы")
    @Test
    void shouldCorrectCopyProperties() {
        var optionalTransportUnit = transportUnitRepository.findById(1L);
        if (optionalTransportUnit.isPresent()) {
            var transportUnit = optionalTransportUnit.get();
            BeanUtils.copyProperties(TU_2, transportUnit, "id");
        }
        assertThat(optionalTransportUnit).get().usingRecursiveComparison().ignoringFields("id").isEqualTo(TU_2);
    }

    private TransportUnit unProxy(TransportUnit transportUnit) {
        transportUnit.setTransportType((TransportType) Hibernate.unproxy(transportUnit.getTransportType()));
        transportUnit.setOrganization((Organization) Hibernate.unproxy(transportUnit.getOrganization()));
        transportUnit.setMunicipality((Municipality) Hibernate.unproxy(transportUnit.getMunicipality()));
        return transportUnit;
    }

}