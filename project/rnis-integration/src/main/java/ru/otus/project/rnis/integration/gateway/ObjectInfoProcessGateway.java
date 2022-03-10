package ru.otus.project.rnis.integration.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface ObjectInfoProcessGateway {

    @Gateway(requestChannel = "inputObjectInfoProcessChannel", replyChannel = "outputObjectInfoProcessChannel", payloadExpression = "''")
    boolean objectInfoProcess();

}