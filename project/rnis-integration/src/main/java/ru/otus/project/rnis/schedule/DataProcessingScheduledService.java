package ru.otus.project.rnis.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.project.rnis.integration.gateway.ObjectInfoProcessGateway;
import ru.otus.project.rnis.integration.gateway.TreeProcessGateway;

@Slf4j
@RequiredArgsConstructor
@Service
public class DataProcessingScheduledService {

    private final static String TREE_PROCESS_START = "Integration: start update tree process";
    private final static String TREE_PROCESS_END = "Integration: end update tree process";

    private final static String OBJECT_INFO_PROCESS_START = "Integration: start update object infos process";
    private final static String OBJECT_INFO_PROCESS_END = "Integration: end update object infos process";

    private final TreeProcessGateway treeProcessGateway;
    private final ObjectInfoProcessGateway objectInfoProcessGateway;

    @Scheduled(initialDelay = 1000 * 10, fixedDelay = Long.MAX_VALUE) // one time with delay before start
    public void updateByTreeOnApplicationReadyEvent() {
        log.info(TREE_PROCESS_START);
        treeProcessGateway.treeProcess();
        log.info(TREE_PROCESS_END);
    }

    @Scheduled(cron = "0 0 0 * * *") // in 00:00:00
    public void updateByTree() {
        log.info(TREE_PROCESS_START);
        treeProcessGateway.treeProcess();
        log.info(TREE_PROCESS_END);
    }

    @Scheduled(fixedRate = 1000 * 60, initialDelay = 1000 * 30) // every minute with delay before start
    public void updateByObjectInfo() {
        log.info(OBJECT_INFO_PROCESS_START);
        objectInfoProcessGateway.objectInfoProcess();
        log.info(OBJECT_INFO_PROCESS_END);
    }
}