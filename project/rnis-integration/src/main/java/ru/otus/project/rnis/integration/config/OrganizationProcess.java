package ru.otus.project.rnis.integration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.integration.dsl.IntegrationFlow;
import ru.otus.project.rnis.converter.TreeConverter;
import ru.otus.project.rnis.service.OrganizationService;

import java.util.List;

@Configuration
public class OrganizationProcess {

    private final static String GET_ORGANIZATIONS_METHOD_NAME = "getOrganizations";
    private final static String SAVE_ALL_AND_RETURN_IDS_METHOD_NAME = "saveAllAndReturnIds";
    private final static String DELETE_ALL_BY_IDS_METHOD_NAME = "deleteAllByIdInBatch";

    private final TreeConverter treeConverter;
    private final OrganizationService organizationService;
    private final TreeProcess treeProcess;

    public OrganizationProcess(TreeConverter treeConverter,
                               OrganizationService organizationService,
                               @Lazy TreeProcess treeProcess) {
        this.treeConverter = treeConverter;
        this.organizationService = organizationService;
        this.treeProcess = treeProcess;
    }

    @Bean
    public IntegrationFlow getOrganizationsFlow() {
        return flow -> flow
                .handle(treeConverter, GET_ORGANIZATIONS_METHOD_NAME)
                .to(saveAllOrganizationsFlow());
    }

    @Bean
    public IntegrationFlow saveAllOrganizationsFlow() {
        return flow -> flow
                .handle(organizationService, SAVE_ALL_AND_RETURN_IDS_METHOD_NAME)
                .to(findAllOrganizationIdsToDeleteFlow());
    }

    @Bean
    public IntegrationFlow findAllOrganizationIdsToDeleteFlow() {
        return flow -> flow
                .<List<Long>, List<Long>>transform(savedOrganizationIds -> {
                    var allOrganizationIds = organizationService.findAllIds();
                    allOrganizationIds.removeAll(savedOrganizationIds);
                    return allOrganizationIds;
                })
                .to(deleteAllByIdInBatchOrganizationsFlow());
    }

    @Bean
    public IntegrationFlow deleteAllByIdInBatchOrganizationsFlow() {
        return flow -> flow
                .handle(organizationService, DELETE_ALL_BY_IDS_METHOD_NAME)
                .to(treeProcess.aggregateEndTreeProcessFlow());
    }
}