package ru.otus.project.rnis.integration.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.*;
import org.springframework.messaging.MessageChannel;
import ru.otus.project.rnis.dto.rnis.TreeDto;
import ru.otus.project.rnis.service.RnisService;

import java.util.Objects;

@RequiredArgsConstructor
@Configuration
public class TreeProcess {

    private final static String GET_TREE_METHOD_NAME = "getTree";
    private final static int TREE_PROCESS_COUNT = 5;

    private final RnisService rnisService;
    private final TransportTypeProcess transportTypeProcess;
    private final MunicipalityProcess municipalityProcess;
    private final OrganizationProcess organizationProcess;
    private final TransportUnitProcess transportUnitProcess;

    @Bean
    public MessageChannel inputTreeProcessChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    public IntegrationFlow startTreeProcessFlow() {
        return IntegrationFlows.from(inputTreeProcessChannel())
                               .to(getTreeFlow());
    }

    @Bean
    public IntegrationFlow getTreeFlow() {
        return flow -> flow
                .handle(rnisService, GET_TREE_METHOD_NAME)
                .<TreeDto, Boolean>route(tree -> Objects.nonNull(tree.getChildren()),
                                         mapping -> mapping
                                                 .subFlowMapping(true, toSubscribesTreeFlow())
                                                 .subFlowMapping(false, IntegrationFlowDefinition::nullChannel));
    }

    @Bean
    public IntegrationFlow toSubscribesTreeFlow() {
        return flow -> flow
                .split()
                .publishSubscribeChannel(publishSubscribe -> publishSubscribe
                        .subscribe(transportTypeProcess.getTransportTypesFlow())
                        .subscribe(municipalityProcess.getMunicipalitiesFlow())
                        .subscribe(organizationProcess.getOrganizationsFlow())
                        .subscribe(transportUnitProcess.getTransportUnitsFlow()));
    }

    @Bean
    public IntegrationFlow aggregateEndTreeProcessFlow() {
        return flow -> flow
                .aggregate(a -> a.releaseStrategy(s -> s.getMessages().size() == TREE_PROCESS_COUNT))
                .to(IntegrationFlowDefinition::nullChannel);
    }
}