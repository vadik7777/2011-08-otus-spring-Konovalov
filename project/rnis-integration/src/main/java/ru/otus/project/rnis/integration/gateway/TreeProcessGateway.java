package ru.otus.project.rnis.integration.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface TreeProcessGateway {

    @Gateway(requestChannel = "inputTreeProcessChannel", payloadExpression = "''")
    void treeProcess();

}