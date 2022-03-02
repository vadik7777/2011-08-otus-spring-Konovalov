package ru.otus.project.rnis.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.project.rnis.dto.rest.*;
import ru.otus.project.rnis.service.NavigationInformationService;

import java.time.Instant;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер для работы с навигационной информацией должен")
@WebMvcTest(NavigationInformationController.class)
class NavigationInformationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NavigationInformationService navigationInformationService;

    @Autowired
    private ObjectMapper objectMapper;

    private final static Long ID_1 = 1L;
    private final static Long ID_2 = 2L;

    private final static TransportUnitDto TU_DTO_1 = initTU1Dto();

    private final static NavigationInformationDto NI_DTO_1 = initNI1Dto();
    private final static NavigationInformationDto NI_DTO_2 = initNI2Dto();

    private final static PageRequest PAGE_REQUEST = PageRequest.of(0, 2, Sort.by("id").descending());
    private final static Long TOTAL = 2L;

    private static TransportUnitDto initTU1Dto() {
        return new TransportUnitDto(ID_1,
                                    "объект1",
                                    "телефон1",
                                    Instant.parse("2022-02-02T00:56:03Z"),
                                    45.1234,
                                    45.1234,
                                    "100 км/ч",
                                    100.0,
                                    new MunicipalityDto(ID_1, "МО1"),
                                    new TransportTypeDto(ID_1, "Тип транспорта1"),
                                    new OrganizationDto(ID_1, "Организация1"));
    }

    private static NavigationInformationDto initNI1Dto() {
        return new NavigationInformationDto(ID_1,
                                            Instant.parse("2022-02-02T00:50:03Z"),
                                            45.1234,
                                            45.1234,
                                            "100 км/ч",
                                            100.0,
                                            TU_DTO_1);
    }

    private static NavigationInformationDto initNI2Dto() {
        return new NavigationInformationDto(ID_2,
                                            Instant.parse("2022-02-02T00:55:03Z"),
                                            45.1234,
                                            45.1234,
                                            "100 км/ч",
                                            100.0,
                                            TU_DTO_1);
    }

    @DisplayName("получать список навигационной информации по идентификатору транспортной единицы")
    @Test
    void shouldCorrectGetAll() throws Exception {
        var navigationInformationDtos = new PageImpl<>(List.of(NI_DTO_2, NI_DTO_1), PAGE_REQUEST, TOTAL);

        String json = objectMapper.writeValueAsString(navigationInformationDtos);
        given(navigationInformationService.findAllByTransportUnitId(ID_1, PAGE_REQUEST))
                .willReturn(new PageImpl<>(List.of(NI_DTO_2, NI_DTO_1), PAGE_REQUEST, TOTAL));

        mockMvc.perform(get("/navigation_information/{id}", ID_1)
                                .param("page", String.valueOf(PAGE_REQUEST.getPageNumber()))
                                .param("size", String.valueOf(PAGE_REQUEST.getPageSize()))
                                .param("sort", PAGE_REQUEST.getSort().toString()
                                                           .replace(":", ",")
                                                           .replace(" ", "")))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(content().json(json));
    }
}