package ru.otus.project.rnis.integration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.integration.dsl.IntegrationFlow;
import ru.otus.project.rnis.converter.TreeConverter;
import ru.otus.project.rnis.service.MunicipalityService;

import java.util.List;

@Configuration
public class MunicipalityProcess {

    private final static String GET_MUNICIPALITIES_METHOD_NAME = "getMunicipalities";
    private final static String SAVE_ALL_AND_RETURN_IDS_METHOD_NAME = "saveAllAndReturnIds";
    private final static String DELETE_ALL_BY_IDS_METHOD_NAME = "deleteAllByIdInBatch";

    private final MunicipalityService municipalityService;
    private final TreeConverter treeConverter;
    private final TreeProcess treeProcess;

    public MunicipalityProcess(MunicipalityService municipalityService,
                               TreeConverter treeConverter,
                               @Lazy TreeProcess treeProcess) {
        this.municipalityService = municipalityService;
        this.treeConverter = treeConverter;
        this.treeProcess = treeProcess;
    }

    @Bean
    public IntegrationFlow getMunicipalitiesFlow() {
        return flow -> flow
                .handle(treeConverter, GET_MUNICIPALITIES_METHOD_NAME)
                .to(saveAllMunicipalitiesFlow());
    }

    @Bean
    public IntegrationFlow saveAllMunicipalitiesFlow() {
        return flow -> flow
                .handle(municipalityService, SAVE_ALL_AND_RETURN_IDS_METHOD_NAME)
                .to(findAllMunicipalityIdsToDeleteFlow());
    }

    @Bean
    public IntegrationFlow findAllMunicipalityIdsToDeleteFlow() {
        return flow -> flow
                .<List<Long>, List<Long>>transform(savedMunicipalitiesIds -> {
                    var allMunicipalityIds = municipalityService.findAllIds();
                    allMunicipalityIds.removeAll(savedMunicipalitiesIds);
                    return allMunicipalityIds;
                })
                .to(deleteAllByIdInBatchMunicipalitiesFlow());
    }

    @Bean
    public IntegrationFlow deleteAllByIdInBatchMunicipalitiesFlow() {
        return flow -> flow
                .handle(municipalityService, DELETE_ALL_BY_IDS_METHOD_NAME)
                .to(treeProcess.aggregateEndTreeProcessFlow());
    }
}