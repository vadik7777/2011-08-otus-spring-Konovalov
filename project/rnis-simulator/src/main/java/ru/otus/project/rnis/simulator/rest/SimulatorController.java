package ru.otus.project.rnis.simulator.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.project.rnis.simulator.dto.ObjectInfoDto;
import ru.otus.project.rnis.simulator.dto.TreeDto;
import ru.otus.project.rnis.simulator.service.SimulatorAuthService;
import ru.otus.project.rnis.simulator.service.SimulatorService;
import ru.otus.project.rnis.simulator.constants.SimulatorConstants;

@RequiredArgsConstructor
@RestController
public class SimulatorController {

    private final SimulatorAuthService simulatorAuthService;
    private final SimulatorService simulatorService;

    @GetMapping("/gettree")
    public TreeDto getTree(@CookieValue(value = SimulatorConstants.SECURE_COOKIE, defaultValue = "") String cookieValue,
                           @RequestParam(required = false) boolean all) {
        if (simulatorAuthService.checkSecureCookieValue(cookieValue)) {
            return simulatorService.getTreeSuccess(all);
        }
        return simulatorService.getTreeFailure();
    }

    @GetMapping("/fullobjinfo")
    public ObjectInfoDto getObjectInfo(@CookieValue(value = SimulatorConstants.SECURE_COOKIE, defaultValue = "") String cookieValue,
                                       @RequestParam(defaultValue = "0") Long oid) {
        if (simulatorAuthService.checkSecureCookieValue(cookieValue)) {
            return simulatorService.getObjectInfoSuccess(oid);
        }
        return simulatorService.getObjectInfoFailure();
    }
}