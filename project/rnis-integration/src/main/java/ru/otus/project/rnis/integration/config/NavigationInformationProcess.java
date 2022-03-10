package ru.otus.project.rnis.integration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.integration.dsl.IntegrationFlow;
import ru.otus.project.rnis.service.NavigationInformationService;

@Configuration
public class NavigationInformationProcess {

    private final static String DELETE_ALL_BY_TRANSPORT_UNIT_IDS_METHOD_NAME = "deleteAllByTransportUnitIds";

    private final NavigationInformationService navigationInformationService;
    private final TreeProcess treeProcess;

    public NavigationInformationProcess(NavigationInformationService navigationInformationService,
                                        @Lazy TreeProcess treeProcess) {
        this.navigationInformationService = navigationInformationService;
        this.treeProcess = treeProcess;
    }

    @Bean
    public IntegrationFlow deleteAllByTransportUnitIdsFlow() {
        return flow -> flow
                .handle(navigationInformationService, DELETE_ALL_BY_TRANSPORT_UNIT_IDS_METHOD_NAME)
                .to(treeProcess.aggregateEndTreeProcessFlow());
    }
}