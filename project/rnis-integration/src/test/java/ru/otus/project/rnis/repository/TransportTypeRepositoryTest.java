package ru.otus.project.rnis.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.project.rnis.entity.TransportType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с траспортными типами должен")
@DataJpaTest
class TransportTypeRepositoryTest {

    @Autowired
    private TransportTypeRepository transportTypeRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private final static Long ID_1 = 1L;
    private final static Long ID_2 = 2L;
    private final static Long ID_3 = 3L;
    private final static TransportType TR_TYPE_1 = new TransportType(ID_1, "Тип транспорта1");
    private final static TransportType TR_TYPE_2 = new TransportType(ID_2, "Тип транспорта2");
    private final static TransportType TR_TYPE_3 = new TransportType(ID_3, "Тип транспорта3");


    @DisplayName("сохранять список транспортных типов")
    @Test
    void shouldCorrectSaveAll() {
        var expectedTransportTypeList = List.of(TR_TYPE_1, TR_TYPE_2);

        transportTypeRepository.saveAll(expectedTransportTypeList);
        expectedTransportTypeList.forEach(transportType -> testEntityManager.detach(transportType));

        var actualTransportTypeList = List.of(
                testEntityManager.find(TransportType.class, ID_1),
                testEntityManager.find(TransportType.class, ID_2));

        assertThat(actualTransportTypeList).usingRecursiveComparison().isEqualTo(expectedTransportTypeList);
    }

    @DisplayName("получать список идентификаторов траспортных типов")
    @Test
    void shouldCorrectFindAllIds() {
        var expectedIds = List.of(ID_1, ID_2, ID_3);
        var actualIds = transportTypeRepository.findAllIds();
        assertThat(actualIds).isEqualTo(expectedIds);
    }

    @DisplayName("удалять список транспортных типов по их идентификаторам")
    @Test
    void shouldCorrectDeleteAllByIdInBatch() {
        var deleteIds = List.of(ID_1, ID_2);

        transportTypeRepository.deleteAllByIdInBatch(deleteIds);
        testEntityManager.flush();

        var query = "select m from TransportType m where m.id in :deleteIds";
        var actualTransportTypeList = testEntityManager.getEntityManager()
                                                       .createQuery(query, TransportType.class)
                                                       .setParameter("deleteIds", deleteIds)
                                                       .getResultList();
        assertThat(actualTransportTypeList).isEmpty();
    }

    @DisplayName("получать список всех транспортных типов")
    @Test
    void shouldCorrectFindAll() {
        var expectedTransportTypeList = List.of(TR_TYPE_1, TR_TYPE_2, TR_TYPE_3);
        var actualMunicipalityList = transportTypeRepository.findAll();
        assertThat(actualMunicipalityList).usingRecursiveComparison().isEqualTo(expectedTransportTypeList);
    }

}