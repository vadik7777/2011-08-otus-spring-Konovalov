package ru.otus.project.rnis.integration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.integration.dsl.IntegrationFlow;
import ru.otus.project.rnis.converter.TreeConverter;
import ru.otus.project.rnis.service.TransportUnitService;

import java.util.List;

@Configuration
public class TransportUnitProcess {

    private final static String GET_TRANSPORT_UNITS_METHOD_NAME = "getTransportUnits";
    private final static String SAVE_ALL_AND_RETURN_IDS_METHOD_NAME = "saveAllAndReturnIds";
    private final static String DELETE_ALL_BY_IDS_METHOD_NAME = "deleteAllByIdInBatch";

    private final TreeConverter treeConverter;
    private final TransportUnitService transportUnitService;
    private final NavigationInformationProcess navigationInformationProcess;
    private final TreeProcess treeProcess;

    public TransportUnitProcess(TreeConverter treeConverter,
                                TransportUnitService transportUnitService,
                                NavigationInformationProcess navigationInformationProcess,
                                @Lazy TreeProcess treeProcess) {
        this.treeConverter = treeConverter;
        this.transportUnitService = transportUnitService;
        this.navigationInformationProcess = navigationInformationProcess;
        this.treeProcess = treeProcess;
    }

    @Bean
    public IntegrationFlow getTransportUnitsFlow() {
        return flow -> flow
                .handle(treeConverter, GET_TRANSPORT_UNITS_METHOD_NAME)
                .to(saveAllTransportUnitsFlow());
    }

    @Bean
    public IntegrationFlow saveAllTransportUnitsFlow() {
        return flow -> flow
                .handle(transportUnitService, SAVE_ALL_AND_RETURN_IDS_METHOD_NAME)
                .to(findAllTransportUnitIdsToDeleteFlow());
    }

    @Bean
    public IntegrationFlow findAllTransportUnitIdsToDeleteFlow() {
        return flow -> flow
                .<List<Long>, List<Long>>transform(savedTransportUnitsIds -> {
                    var allTransportUnitIds = transportUnitService.findAllIds();
                    allTransportUnitIds.removeAll(savedTransportUnitsIds);
                    return allTransportUnitIds;
                })
                .to(toSubscribersForDeleteFlow());
    }

    @Bean
    public IntegrationFlow toSubscribersForDeleteFlow() {
        return flow -> flow
                .publishSubscribeChannel(publishSubscribe -> publishSubscribe
                        .subscribe(deleteAllByIdInBatchTransportUnitsFlow())
                        .subscribe(navigationInformationProcess.deleteAllByTransportUnitIdsFlow()));
    }

    @Bean
    public IntegrationFlow deleteAllByIdInBatchTransportUnitsFlow() {
        return flow -> flow
                .handle(transportUnitService, DELETE_ALL_BY_IDS_METHOD_NAME)
                .to(treeProcess.aggregateEndTreeProcessFlow());
    }
}