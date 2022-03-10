package ru.otus.project.rnis.integration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.integration.dsl.IntegrationFlow;
import ru.otus.project.rnis.converter.TreeConverter;
import ru.otus.project.rnis.service.TransportTypeService;

import java.util.List;

@Configuration
public class TransportTypeProcess {

    private final static String GET_TRANSPORT_TYPES_METHOD_NAME = "getTransportTypes";
    private final static String SAVE_ALL_AND_RETURN_IDS_METHOD_NAME = "saveAllAndReturnIds";
    private final static String DELETE_ALL_BY_IDS_METHOD_NAME = "deleteAllByIdInBatch";

    private final TransportTypeService transportTypeService;
    private final TreeConverter treeConverter;
    private final TreeProcess treeProcess;

    public TransportTypeProcess(TransportTypeService transportTypeService,
                                TreeConverter treeConverter,
                                @Lazy TreeProcess treeProcess) {
        this.transportTypeService = transportTypeService;
        this.treeConverter = treeConverter;
        this.treeProcess = treeProcess;
    }

    @Bean
    public IntegrationFlow getTransportTypesFlow() {
        return flow -> flow
                .handle(treeConverter, GET_TRANSPORT_TYPES_METHOD_NAME)
                .to(saveAllTransportTypesFlow());
    }

    @Bean
    public IntegrationFlow saveAllTransportTypesFlow() {
        return flow -> flow
                .handle(transportTypeService, SAVE_ALL_AND_RETURN_IDS_METHOD_NAME)
                .to(findAllTransportTypeIdsToDeleteFlow());
    }

    @Bean
    public IntegrationFlow findAllTransportTypeIdsToDeleteFlow() {
        return flow -> flow
                .<List<Long>, List<Long>>transform(savedTransportTypeIds -> {
                    var allTransportTypeIds = transportTypeService.findAllIds();
                    allTransportTypeIds.removeAll(savedTransportTypeIds);
                    return allTransportTypeIds;
                })
                .to(deleteAllByIdInBatchTransportTypesFlow());
    }

    @Bean
    public IntegrationFlow deleteAllByIdInBatchTransportTypesFlow() {
        return flow -> flow
                .handle(transportTypeService, DELETE_ALL_BY_IDS_METHOD_NAME)
                .to(treeProcess.aggregateEndTreeProcessFlow());
    }
}