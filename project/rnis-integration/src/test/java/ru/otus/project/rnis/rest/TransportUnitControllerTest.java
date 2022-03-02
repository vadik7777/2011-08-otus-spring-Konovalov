package ru.otus.project.rnis.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.project.rnis.dto.rest.*;
import ru.otus.project.rnis.service.TransportUnitService;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер для работы с транспортными единицами должен")
@WebMvcTest(TransportUnitController.class)
class TransportUnitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransportUnitService transportUnitService;

    @Autowired
    private ObjectMapper objectMapper;

    private final static Long ID_1 = 1L;
    private final static Long ID_2 = 2L;

    private final static TransportUnitSimpleDto TU_SIMPLE_DTO_1 = initSimpleTUDto1();
    private final static TransportUnitSimpleDto TU_SIMPLE_DTO_2 = initSimpleTUDto2();
    private final static TransportUnitDto TU_DTO_1 = initTUDto1();

    private static TransportUnitSimpleDto initSimpleTUDto1() {
        return new TransportUnitSimpleDto(ID_1,
                                          45.1234,
                                          45.1234,
                                          100.0,
                                          new TransportTypeDto(ID_1, "Тип транспорта1"));
    }

    private static TransportUnitSimpleDto initSimpleTUDto2() {
        return new TransportUnitSimpleDto(ID_2,
                                          45.1234,
                                          45.1234,
                                          100.0,
                                          new TransportTypeDto(ID_2, "Тип транспорта2"));
    }

    private static TransportUnitDto initTUDto1() {
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

    @DisplayName("получать список транспортных единиц")
    @Test
    void shouldCorrectGetAll() throws Exception {
        var transportUnitSimpleDtos = List.of(TU_SIMPLE_DTO_1, TU_SIMPLE_DTO_2);

        String json = objectMapper.writeValueAsString(transportUnitSimpleDtos);
        given(transportUnitService.findAll(any())).willReturn(transportUnitSimpleDtos);

        mockMvc.perform(get("/transport_unit"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(content().json(json));
    }

    @DisplayName("получать транспортную единицу по идентификатору")
    @Test
    void shouldCorrectGetById() throws Exception {
        var transportUnitDto = TU_DTO_1;

        String json = objectMapper.writeValueAsString(transportUnitDto);
        given(transportUnitService.findById(any())).willReturn(Optional.of(transportUnitDto));

        mockMvc.perform(get("/transport_unit/{id}", ID_1))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(content().json(json));
    }

    @DisplayName("не получать транспортную единицу по идентификатору")
    @Test
    void shouldInCorrectGetById() throws Exception {
        given(transportUnitService.findById(any())).willReturn(Optional.empty());

        mockMvc.perform(get("/transport_unit/{id}", ID_1))
               .andExpect(status().is4xxClientError());
    }
}