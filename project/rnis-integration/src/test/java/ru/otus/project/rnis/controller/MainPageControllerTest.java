package ru.otus.project.rnis.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Контроллер для главной страницы должен")
@WebMvcTest(MainPageController.class)
class MainPageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Value("${rnis-service.hystrix-dashboard-url}")
    private String hystrixDashboardUrl;

    @Value("${springdoc.swagger-ui.path}")
    private String swaggerUrl;

    @DisplayName("возвращать страницу")
    @Test
    void shouldCorrectGetAddPage() throws Exception {
        mockMvc.perform(get("/"))
               .andExpect(status().isOk())
               .andExpect(view().name("index"))
               .andExpect(content().string(containsString("Интеграция с РНИС")))
               .andExpect(content().string(containsString(hystrixDashboardUrl)))
               .andExpect(content().string(containsString(swaggerUrl)));
    }
}