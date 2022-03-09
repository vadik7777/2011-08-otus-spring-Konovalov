package ru.otus.project.rnis.integration.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;
import ru.otus.project.rnis.converter.ObjectInfoConverter;
import ru.otus.project.rnis.integration.config.util.ProcessUtils;
import ru.otus.project.rnis.service.NavigationInformationService;
import ru.otus.project.rnis.service.RnisService;
import ru.otus.project.rnis.service.TransportUnitService;

import java.util.List;
import java.util.concurrent.Executors;

@Configuration
public class ObjectInfoProcess {

    private final static String FIND_ALL_IDS_METHOD_NAME = "findAllIds";
    private final static String GET_OBJECT_INFO_METHOD_NAME = "getObjectInfo";
    private final static String GET_TRANSPORT_UNIT_METHOD_NAME = "getTransportUnit";
    private final static String GET_NAVIGATION_INFORMATION_METHOD_NAME = "getNavigationInformation";
    private final static String UPDATE_METHOD_NAME = "update";
    private final static String SAVE_METHOD_NAME = "save";
    private final static String GENERATE_END_MESSAGE_METHOD_NAME = "generateEndMessageForObjectInfoProcess";
    private final static int OBJECT_INFO_AGGREGATE_PROCESS_COUNT = 2;

    private final TransportUnitService transportUnitService;
    private final NavigationInformationService navigationInformationService;
    private final RnisService rnisService;
    private final ObjectInfoConverter objectInfoConverter;
    private final boolean navigationInformationEnable;
    private final int objectInfoPoolSize;
    private final ProcessUtils processUtils;

    public ObjectInfoProcess(TransportUnitService transportUnitService,
                             NavigationInformationService navigationInformationService,
                             RnisService rnisService, ObjectInfoConverter objectInfoConverter,
                             @Value("${rnis-service.navigation-information-enable}") boolean navigationInformationEnable,
                             @Value("${rnis-service.object-info-pool-size}") int objectInfoPoolSize,
                             ProcessUtils processUtils) {
        this.transportUnitService = transportUnitService;
        this.navigationInformationService = navigationInformationService;
        this.rnisService = rnisService;
        this.objectInfoConverter = objectInfoConverter;
        this.navigationInformationEnable = navigationInformationEnable;
        this.objectInfoPoolSize = objectInfoPoolSize;
        this.processUtils = processUtils;
    }

    @Bean
    public MessageChannel inputObjectInfoProcessChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    public MessageChannel outputObjectInfoProcessChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    public IntegrationFlow startObjectInfoProcessFlow() {
        return IntegrationFlows.from(inputObjectInfoProcessChannel())
                               .to(findAllTransportUnitIdsFlow());
    }

    @Bean
    public IntegrationFlow findAllTransportUnitIdsFlow() {
        return flow -> flow
                .handle(transportUnitService, FIND_ALL_IDS_METHOD_NAME)
                .<List<Long>, Boolean>route(list -> list.isEmpty(),
                                            mapping -> mapping
                                                    .subFlowMapping(true, endObjectInfoProcessWithOutElements())
                                                    .subFlowMapping(false, getObjectInfoFlow()));
    }

    @Bean
    public IntegrationFlow getObjectInfoFlow() {
        return flow -> flow
                .split()
                .channel(MessageChannels.executor(Executors.newFixedThreadPool(objectInfoPoolSize)))
                .handle(rnisService, GET_OBJECT_INFO_METHOD_NAME)
                .aggregate()
                .to(toSubscribesObjectInfoFlow());
    }

    @Bean
    public IntegrationFlow toSubscribesObjectInfoFlow() {
        return flow -> flow
                .publishSubscribeChannel(p -> {
                    if (navigationInformationEnable) {
                        p.subscribe(getTransportUnitFlow())
                         .subscribe(getNavigationInformationFlow());
                    } else {
                        p.subscribe(getTransportUnitFlow());
                    }
                });
    }

    @Bean
    public IntegrationFlow getTransportUnitFlow() {
        return flow -> flow
                .split()
                .handle(objectInfoConverter, GET_TRANSPORT_UNIT_METHOD_NAME)
                .aggregate()
                .to(updateTransportUnitFlow());
    }

    @Bean
    public IntegrationFlow updateTransportUnitFlow() {
        return flow -> flow
                .split()
                .handle(transportUnitService, UPDATE_METHOD_NAME)
                .aggregate()
                .handle(processUtils, GENERATE_END_MESSAGE_METHOD_NAME)
                .to(navigationInformationEnable ? aggregateEndObjectInfoProcessWithNavigationInformationFlow()
                            : endObjectInfoProcessWithOutNavigationInformationFlow());
    }

    @Bean
    public IntegrationFlow getNavigationInformationFlow() {
        return flow -> flow
                .split()
                .handle(objectInfoConverter, GET_NAVIGATION_INFORMATION_METHOD_NAME)
                .aggregate()
                .to(saveNavigationInformationFlow());
    }

    @Bean
    public IntegrationFlow saveNavigationInformationFlow() {
        return flow -> flow
                .split()
                .handle(navigationInformationService, SAVE_METHOD_NAME)
                .aggregate()
                .handle(processUtils, GENERATE_END_MESSAGE_METHOD_NAME)
                .to(aggregateEndObjectInfoProcessWithNavigationInformationFlow());
    }

    @Bean
    public IntegrationFlow endObjectInfoProcessWithOutElements() {
        return flow -> flow
                .handle(processUtils, GENERATE_END_MESSAGE_METHOD_NAME)
                .channel(outputObjectInfoProcessChannel());
    }

    @Bean
    public IntegrationFlow endObjectInfoProcessWithOutNavigationInformationFlow() {
        return flow -> flow
                .channel(outputObjectInfoProcessChannel());
    }

    @Bean
    public IntegrationFlow aggregateEndObjectInfoProcessWithNavigationInformationFlow() {
        return flow -> flow
                .aggregate(a -> a.releaseStrategy(s -> s.getMessages().size() == OBJECT_INFO_AGGREGATE_PROCESS_COUNT))
                .channel(outputObjectInfoProcessChannel());
    }
}