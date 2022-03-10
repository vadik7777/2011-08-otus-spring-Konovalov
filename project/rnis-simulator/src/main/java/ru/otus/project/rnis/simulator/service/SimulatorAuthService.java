package ru.otus.project.rnis.simulator.service;

import java.util.Optional;

public interface SimulatorAuthService {

    Optional<String> getSecureCookie(String login, String password);

    boolean checkSecureCookieValue(String checkedCookieValue);

}