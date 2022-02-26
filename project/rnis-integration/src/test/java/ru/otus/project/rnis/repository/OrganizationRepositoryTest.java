package ru.otus.project.rnis.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.project.rnis.entity.Organization;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с организациями должен")
@DataJpaTest
class OrganizationRepositoryTest {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private final static Long ID_1 = 1L;
    private final static Long ID_2 = 2L;
    private final static Long ID_3 = 3L;
    private final static Organization ORG_1 =  new Organization(ID_1, "Организация1");
    private final static Organization ORG_2 =  new Organization(ID_2, "Организация2");

    @DisplayName("сохранять список организаций")
    @Test
    void shouldCorrectSaveAll() {
        var expectedOrganizationList = List.of(ORG_1, ORG_2);

        organizationRepository.saveAll(expectedOrganizationList);
        expectedOrganizationList.forEach(organization -> testEntityManager.detach(organization));

        var actualOrganizationList = List.of(
                testEntityManager.find(Organization.class, ID_1),
                testEntityManager.find(Organization.class, ID_2));

        assertThat(actualOrganizationList).usingRecursiveComparison().isEqualTo(expectedOrganizationList);
    }

    @DisplayName("получать список идентификаторов организаций")
    @Test
    void shouldCorrectFindAllIds() {
        var expectedIds = List.of(ID_1, ID_2, ID_3);
        var actualIds = organizationRepository.findAllIds();
        assertThat(actualIds).isEqualTo(expectedIds);
    }

    @DisplayName("удалять список организаций по их идентификаторам")
    @Test
    void shouldCorrectDeleteAllByIdInBatch() {
        var deleteIds = List.of(ID_1, ID_2);

        organizationRepository.deleteAllByIdInBatch(deleteIds);
        testEntityManager.flush();

        var query = "select m from Organization m where m.id in :deleteIds";
        var actualOrganizationList = testEntityManager.getEntityManager()
                                                      .createQuery(query, Organization.class)
                                                      .setParameter("deleteIds", deleteIds)
                                                      .getResultList();

        assertThat(actualOrganizationList).isEmpty();
    }

}