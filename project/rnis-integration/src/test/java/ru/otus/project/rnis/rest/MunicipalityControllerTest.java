package ru.otus.project.rnis.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.project.rnis.dto.rest.MunicipalityDto;
import ru.otus.project.rnis.service.MunicipalityService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер для работы с муниципальными образованиями должен")
@WebMvcTest(MunicipalityController.class)
class MunicipalityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MunicipalityService municipalityService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("получать список муниципальных образований")
    @Test
    void shouldCorrectGetAll() throws Exception {
        var municipalityDtos = List.of(new MunicipalityDto(1L, "МО1"),
                                       new MunicipalityDto(2L, "МО2"));

        String json = objectMapper.writeValueAsString(municipalityDtos);
        given(municipalityService.findAll()).willReturn(municipalityDtos);

        mockMvc.perform(get("/municipality"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(content().json(json));
    }
}