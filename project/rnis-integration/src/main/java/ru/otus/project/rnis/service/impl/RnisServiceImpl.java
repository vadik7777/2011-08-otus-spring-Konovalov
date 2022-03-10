package ru.otus.project.rnis.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import ru.otus.project.rnis.dto.rnis.ObjectInfoDto;
import ru.otus.project.rnis.dto.rnis.TreeDto;
import ru.otus.project.rnis.feign.RnisSecureProxy;
import ru.otus.project.rnis.service.RnisService;

import java.util.Objects;

import static ru.otus.project.rnis.constants.RnisConstants.ID_WRONG_OBJECT_INFO;
import static ru.otus.project.rnis.constants.RnisConstants.RNIS_RESULT_SUCCESS_RESPONSE;

@Slf4j
@Service
public class RnisServiceImpl implements RnisService {

    private final RnisSecureProxy rnisSecureProxy;
    private final RnisServiceImpl self;

    public RnisServiceImpl(RnisSecureProxy rnisSecureProxy, @Lazy RnisServiceImpl self) {
        this.rnisSecureProxy = rnisSecureProxy;
        this.self = self;
    }

    @Override
    public ResponseEntity<String> ping() {
        return rnisSecureProxy.ping();
    }

    @HystrixCommand(groupKey = "rnis", commandKey = "get object info from rnis", fallbackMethod = "getObjectInfoFallbackMethod",
            threadPoolKey = "objectInfoPool")
    @Override
    public ObjectInfoDto getObjectInfo(Long oid) {
        return self.getObjectInfoWithHystrix(oid);
    }

    @Retryable
    public ObjectInfoDto getObjectInfoWithHystrix(Long oid) {
        var objectInfoDto = rnisSecureProxy.fullObjInfo(oid);
        if (!Objects.equals(RNIS_RESULT_SUCCESS_RESPONSE, objectInfoDto.getResult())) {
            throw new RuntimeException();
        }
        return objectInfoDto;
    }

    public ObjectInfoDto getObjectInfoFallbackMethod(Long oid) {
        log.error("Failure get object info data from rnis with id = {}, return default empty object info with id = 0", oid);
        return new ObjectInfoDto(ID_WRONG_OBJECT_INFO);
    }

    @HystrixCommand(groupKey = "rnis", commandKey = "get tree from rnis", fallbackMethod = "getTreeFallbackMethod")
    @Override
    public TreeDto getTree() {
        return self.getTreeWithHystrix();
    }

    @Retryable
    public TreeDto getTreeWithHystrix() {
        var treeDto = rnisSecureProxy.getTree(true);
        if (!Objects.equals(RNIS_RESULT_SUCCESS_RESPONSE, treeDto.getResult())) {
            throw new RuntimeException();
        }
        return treeDto;
    }

    public TreeDto getTreeFallbackMethod() {
        log.error("Failure get tree data from rnis, return default empty object");
        return new TreeDto();
    }
}