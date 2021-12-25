package ru.otus.homework15.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.homework15.domain.Matryoshka;
import ru.otus.homework15.dto.MatryoshkaDto;
import ru.otus.homework15.mapper.MatryoshkaMapper;
import ru.otus.homework15.repository.MatryoshkaRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;


@DisplayName("Сервис для работы с матрешками должен")
@SpringBootTest
class MatryoshkaServiceTest {

    @MockBean
    private MatryoshkaRepository matryoshkaRepository;

    @MockBean
    private MatryoshkaMapper matryoshkaMapper;

    @Autowired
    private MatryoshkaServiceImpl matryoshkaService;

    @DisplayName("добавлять матрешку")
    @Test
    void shouldCorrectCreate() {
        Matryoshka inputMatryoshka = new Matryoshka();
        inputMatryoshka.setId(1);
        inputMatryoshka.setStep(1);
        inputMatryoshka.setMaxSteps(2);

        Matryoshka outputMatryoshka = new Matryoshka();
        outputMatryoshka.setId(2);
        outputMatryoshka.setStep(2);
        outputMatryoshka.setMaxSteps(2);
        outputMatryoshka.setMatryoshka(inputMatryoshka);

        MatryoshkaDto inputMatryoshkaDto = new MatryoshkaDto();
        inputMatryoshkaDto.setId(1);
        inputMatryoshkaDto.setStep(1);
        inputMatryoshkaDto.setMaxSteps(2);

        MatryoshkaDto outputMatryoshkaDto = new MatryoshkaDto();
        outputMatryoshkaDto.setStep(2);
        outputMatryoshkaDto.setMaxSteps(2);
        outputMatryoshkaDto.setMatryoshka(inputMatryoshkaDto);

        given(matryoshkaMapper.toEntity(eq(outputMatryoshkaDto))).willReturn(outputMatryoshka);
        given(matryoshkaRepository.save(outputMatryoshka)).willReturn(outputMatryoshka);
        given(matryoshkaMapper.toDto(eq(outputMatryoshka))).willReturn(outputMatryoshkaDto);

        var actualMatryoshkaDto = matryoshkaService.create(inputMatryoshkaDto);

        assertThat(actualMatryoshkaDto).usingRecursiveComparison().isEqualTo(outputMatryoshkaDto);
    }
}