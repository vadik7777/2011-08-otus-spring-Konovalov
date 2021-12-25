package ru.otus.homework15.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework15.dto.MatryoshkaDto;
import ru.otus.homework15.integration.gateway.MatryoshkaGateway;
import ru.otus.homework15.service.MatryoshkaService;

import java.util.Arrays;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommands {

    private final MatryoshkaGateway matryoshkaGateway;
    private final MatryoshkaService matryoshkaService;

    @ShellMethod(value = "Insert steps to wrap matryoshkas - enter: steps to first, steps to second,  etc",
            key = {"w", "wrap"})
    public String startWrap(@ShellOption(defaultValue = "1, 2, 3") int... steps) {
        var matryoshkaDtos = Arrays.stream(steps).mapToObj(MatryoshkaDto::new).collect(Collectors.toList());
        var wraps = matryoshkaGateway.wrap(matryoshkaDtos);
        return "insert matryoshkas successful: \n" +
                wraps.stream().map(MatryoshkaDto::toString).collect(Collectors.joining("\n"));
    }

    @ShellMethod(value = "Get all matryoshkas", key = {"a", "all"})
    public String getAllMatryoshkas() {
        var matryoshkaDtos = matryoshkaService.findAll();
        if (matryoshkaDtos.isEmpty()) {
            return "get all matryoshkas successful: result is empty";
        } else {
            return "get all matryoshkas successful: \n" +
                    matryoshkaDtos.stream().map(MatryoshkaDto::toString).collect(Collectors.joining("\n"));
        }
    }
}