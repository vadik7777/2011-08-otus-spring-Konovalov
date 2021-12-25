package ru.otus.homework15.integration.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.homework15.dto.MatryoshkaDto;

import java.util.List;

@MessagingGateway
public interface MatryoshkaGateway {
    @Gateway(requestChannel = "matreshkaInputChannel", replyChannel = "matreshkaOutputChannel")
    List<MatryoshkaDto> wrap(List<MatryoshkaDto> matryoshkaDtos);
}
