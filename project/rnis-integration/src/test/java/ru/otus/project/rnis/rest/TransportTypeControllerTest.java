package ru.otus.project.rnis.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.project.rnis.dto.rest.TransportTypeDto;
import ru.otus.project.rnis.service.TransportTypeService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер для работы с транспортными типами должен")
@WebMvcTest(TransportTypeController.class)
class TransportTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransportTypeService transportTypeService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("получать список транспортных типов")
    @Test
    void shouldCorrectGetAll() throws Exception {
        var transportTypeDtos = List.of(new TransportTypeDto(1L, "Транспортный тип 1"),
                                        new TransportTypeDto(2L, "Транспортный тип 2"));

        String json = objectMapper.writeValueAsString(transportTypeDtos);
        given(transportTypeService.findAll()).willReturn(transportTypeDtos);

        mockMvc.perform(get("/transport_type"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(content().json(json));
    }
}