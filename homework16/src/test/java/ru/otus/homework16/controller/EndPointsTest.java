package ru.otus.homework16.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Actuator должен")
@WithMockUser(authorities = "ROLE_ADMIN")
@AutoConfigureMockMvc
@SpringBootTest
class EndPointsTest {

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(authorities = "ROLE_ADMIN")
    @DisplayName("предоставлять доступ под ролью ADMIN к index странице")
    @Test
    void shouldCorrectGetListPage() throws Exception {
        mockMvc.perform(get("/actuator"))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "ROLE_ADMIN")
    @DisplayName("предоставлять доступ под ролью ADMIN к logfile странице")
    @Test
    void shouldCorrectGetLogfilePage() throws Exception {
        mockMvc.perform(get("/actuator/logfile"))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "ROLE_UNKNOWN")
    @DisplayName("делать редирект на страницу ошибки при несоответствии роли ADMIN")
    @Test
    void shouldInCorrectGetListPage() throws Exception {
        mockMvc.perform(get("/actuator"))
                .andExpect(status().is4xxClientError());
    }

    @WithMockUser(authorities = "ROLE_ADMIN")
    @DisplayName("предоставлять доступ под ролью ADMIN к health странице и получить сообщение о работающем someservice")
    @Test
    void shouldCorrectGetHealthAndSomeserviceUpPage() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Some service is active")));
    }
}