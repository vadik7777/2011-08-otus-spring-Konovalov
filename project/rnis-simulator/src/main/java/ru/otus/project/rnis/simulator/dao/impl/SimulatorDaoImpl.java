package ru.otus.project.rnis.simulator.dao.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.otus.project.rnis.simulator.dao.SimulatorDao;
import ru.otus.project.rnis.simulator.dao.impl.util.AsyncTreeWalker;
import ru.otus.project.rnis.simulator.domain.ObjectInfo;
import ru.otus.project.rnis.simulator.domain.Property;
import ru.otus.project.rnis.simulator.domain.Sensor;
import ru.otus.project.rnis.simulator.domain.Tree;
import ru.otus.project.rnis.simulator.constants.SimulatorConstants;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SimulatorDaoImpl implements SimulatorDao {

    private final static int OBJECT_INFO_LEVEL = 3;
    private final static Tree TREE_CLEAN = new Tree(null, SimulatorConstants.RESULT_SUCCESS_RESPONSE);
    private final static Tree TREE_FAILURE = new Tree(null, SimulatorConstants.RESULT_FAILURE_RESPONSE);
    private final static ObjectInfo OBJECT_INFO_FAILURE = initObjectInfoFailure();
    private final static ObjectInfo OBJECT_INFO_NO_PERMISSIONS = initObjectInfoNoPermissions(OBJECT_INFO_FAILURE);
    private final static ObjectInfo OBJECT_INFO_WITH_TEL_AND_SPEED = initObjectInfoWithTelAndSpeed();

    private final Resource treeJson;
    private final ObjectMapper objectMapper;
    private final AsyncTreeWalker asyncTreeWalker;
    private final Map<Long, ObjectInfo> objectInfoMap = new ConcurrentHashMap<>();

    private Tree tree;

    public SimulatorDaoImpl(@Value("${simulator.tree-json}") Resource treeJson,
                            ObjectMapper objectMapper,
                            AsyncTreeWalker asyncTreeWalker) {
        this.treeJson = treeJson;
        this.objectMapper = objectMapper;
        this.asyncTreeWalker = asyncTreeWalker;
    }

    private static ObjectInfo initObjectInfoFailure() {
        var objectInfoFailure = new ObjectInfo();
        objectInfoFailure.setOid(0L);
        objectInfoFailure.setName(null);
        objectInfoFailure.setImei(null);
        objectInfoFailure.setCid(0L);
        objectInfoFailure.setDt(Instant.parse("0001-01-01T00:00:00Z"));
        objectInfoFailure.setProperties(List.of());
        objectInfoFailure.setSensors(List.of());
        objectInfoFailure.setResult(SimulatorConstants.RESULT_FAILURE_RESPONSE);
        objectInfoFailure.setParentId(0L);
        objectInfoFailure.setAddress(null);
        objectInfoFailure.setObjIcon(null);
        objectInfoFailure.setObjIconHeight(0);
        objectInfoFailure.setObjIconWidth(0);
        objectInfoFailure.setObjIconRotate(false);
        objectInfoFailure.setStatus(0);
        objectInfoFailure.setLat(0.);
        objectInfoFailure.setLon(0.);
        objectInfoFailure.setDirection(0.);
        objectInfoFailure.setMove(0);
        objectInfoFailure.setBlockReason(0);

        return objectInfoFailure;
    }

    private static ObjectInfo initObjectInfoNoPermissions(ObjectInfo objectInfoFailure) {
        var objectInfoNoPermissions = new ObjectInfo();
        BeanUtils.copyProperties(objectInfoFailure, objectInfoNoPermissions);
        objectInfoNoPermissions.setResult(SimulatorConstants.RESULT_NO_PERMISSION_RESPONSE);

        return objectInfoNoPermissions;
    }

    private static ObjectInfo initObjectInfoWithTelAndSpeed() {
        var properties = List.of(new Property("Телефонный номер", "+79041708083"));
        var sensors = List.of(new Sensor(null, null, null, "Скорость", "100 км/ч", null, true));

        var objectInfoWithTelAndSpeed = new ObjectInfo();
        objectInfoWithTelAndSpeed.setProperties(properties);
        objectInfoWithTelAndSpeed.setSensors(sensors);
        objectInfoWithTelAndSpeed.setOid(SimulatorConstants.ID_OBJECT_INFO_WITH_TEL_AND_SENSORS);
        objectInfoWithTelAndSpeed.setName("Транспортная единица " + SimulatorConstants.ID_OBJECT_INFO_WITH_TEL_AND_SENSORS);
        objectInfoWithTelAndSpeed.setImei("IMEI" + SimulatorConstants.ID_OBJECT_INFO_WITH_TEL_AND_SENSORS);
        objectInfoWithTelAndSpeed.setCid(null);
        objectInfoWithTelAndSpeed.setDt(Instant.now());
        objectInfoWithTelAndSpeed.setProperties(properties);
        objectInfoWithTelAndSpeed.setSensors(sensors);
        objectInfoWithTelAndSpeed.setResult(SimulatorConstants.RESULT_SUCCESS_RESPONSE);
        objectInfoWithTelAndSpeed.setParentId(530L);
        objectInfoWithTelAndSpeed.setAddress(null);
        objectInfoWithTelAndSpeed.setObjIcon("");
        objectInfoWithTelAndSpeed.setObjIconHeight(0);
        objectInfoWithTelAndSpeed.setObjIconWidth(0);
        objectInfoWithTelAndSpeed.setObjIconRotate(false);
        objectInfoWithTelAndSpeed.setStatus(5);
        objectInfoWithTelAndSpeed.setLat(48.61654);
        objectInfoWithTelAndSpeed.setLon(42.81458);
        objectInfoWithTelAndSpeed.setDirection(207.0);
        objectInfoWithTelAndSpeed.setMove(100);
        objectInfoWithTelAndSpeed.setBlockReason(0);

        return objectInfoWithTelAndSpeed;
    }

    @PostConstruct
    public void init() {
        try {
            tree = objectMapper.readValue(treeJson.getInputStream(), Tree.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        asyncTreeWalker.treeWalk(OBJECT_INFO_LEVEL, tree, objectInfoMap);
    }

    @Override
    public ObjectInfo getObjectInfoSuccess(Long oid) {
        var objectInfo = objectInfoMap.get(oid);
        if (Objects.nonNull(objectInfo)) {
            objectInfo.setDt(Instant.now());
            return objectInfo;
        } else {
            return OBJECT_INFO_NO_PERMISSIONS;
        }
    }

    @Override
    public ObjectInfo getObjectInfoWithTelAndSpeed() {
        return OBJECT_INFO_WITH_TEL_AND_SPEED;
    }

    @Override
    public ObjectInfo getObjectInfoFailure() {
        return OBJECT_INFO_FAILURE;
    }

    @Override
    public Tree getTreeSuccess() {
        return tree;
    }

    @Override
    public Tree getTreeCleanSuccess() {
        return TREE_CLEAN;
    }

    @Override
    public Tree getTreeFailure() {
        return TREE_FAILURE;
    }

}