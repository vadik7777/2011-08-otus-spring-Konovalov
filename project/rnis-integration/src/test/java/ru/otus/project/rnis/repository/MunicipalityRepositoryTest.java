package ru.otus.project.rnis.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.project.rnis.entity.Municipality;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с МО должен")
@DataJpaTest
class MunicipalityRepositoryTest {

    @Autowired
    private MunicipalityRepository municipalityRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private final static Long ID_1 = 1L;
    private final static Long ID_2 = 2L;
    private final static Long ID_3 = 3L;
    private final static Municipality MO_1 = new Municipality(ID_1, "МО1");
    private final static Municipality MO_2 = new Municipality(ID_2, "МО2");
    private final static Municipality MO_3 = new Municipality(ID_3, "МО3");

    @DisplayName("сохранять список МО")
    @Test
    void shouldCorrectSaveAll() {
        var expectedMunicipalityList = List.of(MO_1, MO_2);

        municipalityRepository.saveAll(expectedMunicipalityList);
        expectedMunicipalityList.forEach(municipality -> testEntityManager.detach(municipality));

        var actualMunicipalityList = List.of(
                testEntityManager.find(Municipality.class, ID_1),
                testEntityManager.find(Municipality.class, ID_2));

        assertThat(actualMunicipalityList).usingRecursiveComparison().isEqualTo(expectedMunicipalityList);
    }

    @DisplayName("получать список идентификаторов МО")
    @Test
    void shouldCorrectFindAllIds() {
        var expectedIds = List.of(ID_1, ID_2, ID_3);
        var actualIds = municipalityRepository.findAllIds();
        assertThat(actualIds).isEqualTo(expectedIds);
    }

    @DisplayName("удалять список МО по их идентификаторам")
    @Test
    void shouldCorrectDeleteAllByIdInBatch() {
        var deleteIds = List.of(ID_1, ID_2);

        municipalityRepository.deleteAllByIdInBatch(deleteIds);
        testEntityManager.flush();

        var query = "select m from Municipality m where m.id in :deleteIds";
        var actualMunicipalityList = testEntityManager.getEntityManager()
                                                      .createQuery(query, Municipality.class)
                                                      .setParameter("deleteIds", deleteIds)
                                                      .getResultList();
        assertThat(actualMunicipalityList).isEmpty();
    }

    @DisplayName("получать список всех МО")
    @Test
    void shouldCorrectFindAll() {
        var expectedMunicipalityList = List.of(MO_1, MO_2, MO_3);
        var actualMunicipalityList = municipalityRepository.findAll();
        assertThat(actualMunicipalityList).usingRecursiveComparison().isEqualTo(expectedMunicipalityList);
    }

}