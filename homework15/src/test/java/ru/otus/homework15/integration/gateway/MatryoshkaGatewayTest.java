package ru.otus.homework15.integration.gateway;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.homework15.dto.MatryoshkaDto;
import ru.otus.homework15.service.MatryoshkaService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@DisplayName("Гейтвей для работы с матрешками должен")
@SpringBootTest
class MatryoshkaGatewayTest {

    @MockBean
    private MatryoshkaService matryoshkaService;

    @Autowired
    private MatryoshkaGateway matryoshkaGateway;

    @DisplayName("создавать матрешку")
    @Test
    void shouldCorrectCreate() {
        var inputMatryoshkaDto = new MatryoshkaDto(3);
        inputMatryoshkaDto.setId(1L);

        var expectedMatryoshkaDto1 = new MatryoshkaDto();
        expectedMatryoshkaDto1.setStep(1);
        expectedMatryoshkaDto1.setMaxSteps(3);

        var expectedMatryoshkaDto2 = new MatryoshkaDto();
        expectedMatryoshkaDto2.setStep(2);
        expectedMatryoshkaDto2.setMaxSteps(3);
        expectedMatryoshkaDto2.setMatryoshka(expectedMatryoshkaDto1);

        var expectedMatryoshkaDto = new MatryoshkaDto();
        expectedMatryoshkaDto.setStep(3);
        expectedMatryoshkaDto.setMaxSteps(3);
        expectedMatryoshkaDto.setMatryoshka(expectedMatryoshkaDto2);

        given(matryoshkaService.create(inputMatryoshkaDto)).willReturn(expectedMatryoshkaDto);
        given(matryoshkaService.create(expectedMatryoshkaDto)).willReturn(expectedMatryoshkaDto1);
        given(matryoshkaService.create(expectedMatryoshkaDto1)).willReturn(expectedMatryoshkaDto2);


        var matryoshkaDtoList = matryoshkaGateway.wrap(List.of(inputMatryoshkaDto));
        var actualMatryoshkaDto = matryoshkaDtoList.get(0);

        assertThat(actualMatryoshkaDto).usingRecursiveComparison().isEqualTo(expectedMatryoshkaDto);
    }
}