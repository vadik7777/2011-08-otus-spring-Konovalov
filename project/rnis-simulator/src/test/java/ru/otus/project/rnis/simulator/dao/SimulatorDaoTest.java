package ru.otus.project.rnis.simulator.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SyncTaskExecutor;
import ru.otus.project.rnis.simulator.domain.ObjectInfo;
import ru.otus.project.rnis.simulator.domain.Property;
import ru.otus.project.rnis.simulator.domain.Sensor;
import ru.otus.project.rnis.simulator.domain.Tree;
import ru.otus.project.rnis.simulator.constants.SimulatorConstants;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.Executor;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Объект доступа к данным должен")
@SpringBootTest
class SimulatorDaoTest {

    @Autowired
    private SimulatorDao simulatorDao;

    private final static Long ID_OBJECT_INFO_SUCCESS = 1L;
    private final static Long ID_OBJECT_INFO_NO_PERMISSION = 2L;
    private final static ObjectInfo OBJECT_INFO_SUCCESS = initObjectInfoSuccess();
    private final static ObjectInfo OBJECT_INFO_WITH_TEL_AND_SPEED = initObjectInfoWithTelAndSpeed();
    private final static ObjectInfo OBJECT_INFO_FAILURE = initObjectInfoFailure();
    private final static ObjectInfo OBJECT_INFO_NO_PERMISSIONS = initObjectInfoNoPermissions(OBJECT_INFO_FAILURE);
    private final static Tree TREE_CLEAN = new Tree(null, SimulatorConstants.RESULT_SUCCESS_RESPONSE);
    private final static Tree TREE_FAILURE = new Tree(null, SimulatorConstants.RESULT_FAILURE_RESPONSE);

    private static ObjectInfo initObjectInfoSuccess() {
        var objectInfoSuccess = new ObjectInfo();
        objectInfoSuccess.setOid(ID_OBJECT_INFO_SUCCESS);
        objectInfoSuccess.setName("Транспортная единица 1");
        objectInfoSuccess.setImei("IMEI1");
        objectInfoSuccess.setCid(null);
        objectInfoSuccess.setProperties(null);
        objectInfoSuccess.setSensors(null);
        objectInfoSuccess.setResult(SimulatorConstants.RESULT_SUCCESS_RESPONSE);
        objectInfoSuccess.setParentId(6L);
        objectInfoSuccess.setAddress(null);
        objectInfoSuccess.setObjIcon("");
        objectInfoSuccess.setObjIconHeight(0);
        objectInfoSuccess.setObjIconWidth(0);
        objectInfoSuccess.setObjIconRotate(false);
        objectInfoSuccess.setStatus(5);
        objectInfoSuccess.setLat(48.75955);
        objectInfoSuccess.setLon(44.51461);
        objectInfoSuccess.setDirection(207.0);
        objectInfoSuccess.setMove(110);
        objectInfoSuccess.setBlockReason(0);

        return objectInfoSuccess;
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

    @TestConfiguration
    static class SimulatorDaoTestConfiguration {

        @Bean
        public Executor threadPoolTaskExecutor() {
            return new SyncTaskExecutor();
        }
    }

    @DisplayName("получить дерево объектов")
    @Test
    void shouldCorrectGetTreeSuccess() {
        var actualTree = simulatorDao.getTreeSuccess();
        assertThat(actualTree.getResult()).isEqualTo(SimulatorConstants.RESULT_SUCCESS_RESPONSE);
    }

    @DisplayName("получить пустое дерево объектов")
    @Test
    void shouldCorrectGetTreeCleanSuccess() {
        var actualTree = simulatorDao.getTreeCleanSuccess();
        assertThat(actualTree).isEqualTo(TREE_CLEAN);
    }

    @DisplayName("получить ошибочное дерево объектов")
    @Test
    void shouldCorrectGetTreeFailure() {
        var actualTree = simulatorDao.getTreeFailure();
        assertThat(actualTree).isEqualTo(TREE_FAILURE);
    }

    @DisplayName("получить информацию об объекте")
    @Test
    void shouldCorrectObjectInfoSuccess() {
        simulatorDao.getTreeSuccess();
        var actualObjectInfo = simulatorDao.getObjectInfoSuccess(ID_OBJECT_INFO_SUCCESS);
        assertThat(actualObjectInfo).usingRecursiveComparison().ignoringFields("dt").isEqualTo(OBJECT_INFO_SUCCESS);
    }

    @DisplayName("получить информацию об объекте с телефоном и скоростью")
    @Test
    void shouldCorrectObjectInfoSuccessWithTelAndSpeed() {
        simulatorDao.getTreeSuccess();
        var actualObjectInfo = simulatorDao.getObjectInfoWithTelAndSpeed();
        assertThat(actualObjectInfo).usingRecursiveComparison()
                                    .ignoringFields("dt")
                                    .isEqualTo(OBJECT_INFO_WITH_TEL_AND_SPEED);
    }

    @DisplayName("получить информацию об объекте с несуществующим идентификатором")
    @Test
    void shouldCorrectObjectInfoNoPermission() {
        simulatorDao.getTreeSuccess();
        var actualObjectInfo = simulatorDao.getObjectInfoSuccess(ID_OBJECT_INFO_NO_PERMISSION);
        assertThat(actualObjectInfo).isEqualTo(OBJECT_INFO_NO_PERMISSIONS);
    }

    @DisplayName("получить ошибочную информацию об объекте")
    @Test
    void shouldCorrectGetObjectInfoFailure() {
        var actualObjectInfo = simulatorDao.getObjectInfoFailure();
        assertThat(actualObjectInfo).isEqualTo(OBJECT_INFO_FAILURE);
    }

}