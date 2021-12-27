package ru.otus.homework15.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework15.domain.Matryoshka;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с матрешками должен")
@Import(DataSourceAutoConfiguration.class)
@DataJpaTest
class MatryoshkaRepositoryTest {

    @Autowired
    private MatryoshkaRepository matryoshkaRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @DisplayName("добавлять матрешку")
    @Test
    void shouldCorrectInsert() {

        var expectedMatryoshka1 = new Matryoshka();
        expectedMatryoshka1.setStep(1);
        expectedMatryoshka1.setMaxSteps(2);

        var expectedMatryoshka2 = new Matryoshka();
        expectedMatryoshka2.setStep(2);
        expectedMatryoshka2.setMaxSteps(2);
        expectedMatryoshka2.setMatryoshka(expectedMatryoshka1);

        matryoshkaRepository.save(expectedMatryoshka1);
        matryoshkaRepository.save(expectedMatryoshka2);

        testEntityManager.detach(expectedMatryoshka1);
        testEntityManager.detach(expectedMatryoshka2);

        var actualMatryoshka2 = testEntityManager.find(Matryoshka.class, expectedMatryoshka2.getId());

        assertThat(actualMatryoshka2).usingRecursiveComparison().isEqualTo(expectedMatryoshka2);
    }

    @DisplayName("загружать список всех матрешек")
    @Test
    void shouldCorrectFindAll() {

        var matryoshkas = matryoshkaRepository.findAll();

        assertThat(matryoshkas).isNotNull().hasSize(2)
                .allMatch(m -> m.getStep() != 0)
                .allMatch(m -> m.getMaxSteps() == 2);
    }
}