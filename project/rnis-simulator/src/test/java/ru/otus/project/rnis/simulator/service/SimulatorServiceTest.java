package ru.otus.project.rnis.simulator.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.project.rnis.simulator.dao.SimulatorDao;
import ru.otus.project.rnis.simulator.domain.ObjectInfo;
import ru.otus.project.rnis.simulator.domain.Property;
import ru.otus.project.rnis.simulator.domain.Sensor;
import ru.otus.project.rnis.simulator.domain.Tree;
import ru.otus.project.rnis.simulator.dto.ObjectInfoDto;
import ru.otus.project.rnis.simulator.dto.PropertyDto;
import ru.otus.project.rnis.simulator.dto.SensorDto;
import ru.otus.project.rnis.simulator.dto.TreeDto;
import ru.otus.project.rnis.simulator.constants.SimulatorConstants;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("Сервис симулятора должен")
@SpringBootTest
class SimulatorServiceTest {

    @MockBean
    private SimulatorDao simulatorDao;

    @Autowired
    private SimulatorService simulatorService;

    private final static Long ID_OBJECT_INFO_SUCCESS = 1L;
    private final static Long ID_OBJECT_INFO_NO_PERMISSION = 2L;
    private final static ObjectInfoDto OBJECT_INFO_SUCCESS_DTO = initObjectInfoSuccessDto();
    private final static ObjectInfoDto OBJECT_INFO_WITH_TEL_AND_SPEED_DTO = initObjectInfoWithTelAndSpeedDto();
    private final static ObjectInfoDto OBJECT_INFO_FAILURE_DTO = initObjectInfoFailureDto();
    private final static ObjectInfoDto OBJECT_INFO_NO_PERMISSIONS_DTO = initObjectInfoNoPermissionsDto(
            OBJECT_INFO_FAILURE_DTO);
    private final static TreeDto TREE_SUCCESS_DTO = new TreeDto(null, SimulatorConstants.RESULT_SUCCESS_RESPONSE);
    private final static TreeDto TREE_CLEAN_DTO = new TreeDto(null, SimulatorConstants.RESULT_SUCCESS_RESPONSE);
    private final static TreeDto TREE_FAILURE_DTO = new TreeDto(null, SimulatorConstants.RESULT_FAILURE_RESPONSE);

    private final static ObjectInfo OBJECT_INFO_SUCCESS = initObjectInfoSuccess();
    private final static ObjectInfo OBJECT_INFO_WITH_TEL_AND_SPEED = initObjectInfoWithTelAndSpeed();
    private final static ObjectInfo OBJECT_INFO_FAILURE = initObjectInfoFailure();
    private final static ObjectInfo OBJECT_INFO_NO_PERMISSIONS = initObjectInfoNoPermissions(OBJECT_INFO_FAILURE);
    private final static Tree TREE_SUCCESS = new Tree(null, SimulatorConstants.RESULT_SUCCESS_RESPONSE);
    private final static Tree TREE_CLEAN = new Tree(null, SimulatorConstants.RESULT_SUCCESS_RESPONSE);
    private final static Tree TREE_FAILURE = new Tree(null, SimulatorConstants.RESULT_FAILURE_RESPONSE);

    private static ObjectInfoDto initObjectInfoSuccessDto() {
        var objectInfoSuccessDto = new ObjectInfoDto();
        objectInfoSuccessDto.setOid(ID_OBJECT_INFO_SUCCESS);
        objectInfoSuccessDto.setName("Транспортная единица 1");
        objectInfoSuccessDto.setImei("IMEI1");
        objectInfoSuccessDto.setCid(null);
        objectInfoSuccessDto.setProperties(null);
        objectInfoSuccessDto.setSensors(null);
        objectInfoSuccessDto.setResult(SimulatorConstants.RESULT_SUCCESS_RESPONSE);
        objectInfoSuccessDto.setParentId(6L);
        objectInfoSuccessDto.setAddress(null);
        objectInfoSuccessDto.setObjIcon("");
        objectInfoSuccessDto.setObjIconHeight(0);
        objectInfoSuccessDto.setObjIconWidth(0);
        objectInfoSuccessDto.setObjIconRotate(false);
        objectInfoSuccessDto.setStatus(5);
        objectInfoSuccessDto.setLat(48.75955);
        objectInfoSuccessDto.setLon(44.51461);
        objectInfoSuccessDto.setDirection(207.0);
        objectInfoSuccessDto.setMove(110);
        objectInfoSuccessDto.setBlockReason(0);

        return objectInfoSuccessDto;
    }

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

    private static ObjectInfoDto initObjectInfoFailureDto() {
        var objectInfoFailureDto = new ObjectInfoDto();
        objectInfoFailureDto.setOid(0L);
        objectInfoFailureDto.setName(null);
        objectInfoFailureDto.setImei(null);
        objectInfoFailureDto.setCid(0L);
        objectInfoFailureDto.setDt(Instant.parse("0001-01-01T00:00:00Z"));
        objectInfoFailureDto.setProperties(List.of());
        objectInfoFailureDto.setSensors(List.of());
        objectInfoFailureDto.setResult(SimulatorConstants.RESULT_FAILURE_RESPONSE);
        objectInfoFailureDto.setParentId(0L);
        objectInfoFailureDto.setAddress(null);
        objectInfoFailureDto.setObjIcon(null);
        objectInfoFailureDto.setObjIconHeight(0);
        objectInfoFailureDto.setObjIconWidth(0);
        objectInfoFailureDto.setObjIconRotate(false);
        objectInfoFailureDto.setStatus(0);
        objectInfoFailureDto.setLat(0.);
        objectInfoFailureDto.setLon(0.);
        objectInfoFailureDto.setDirection(0.);
        objectInfoFailureDto.setMove(0);
        objectInfoFailureDto.setBlockReason(0);

        return objectInfoFailureDto;
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

    private static ObjectInfoDto initObjectInfoNoPermissionsDto(ObjectInfoDto objectInfoFailureDto) {
        var objectInfoNoPermissionsDto = new ObjectInfoDto();
        BeanUtils.copyProperties(objectInfoFailureDto, objectInfoNoPermissionsDto);
        objectInfoNoPermissionsDto.setResult(SimulatorConstants.RESULT_NO_PERMISSION_RESPONSE);

        return objectInfoNoPermissionsDto;
    }

    private static ObjectInfo initObjectInfoNoPermissions(ObjectInfo objectInfoFailure) {
        var objectInfoNoPermissions = new ObjectInfo();
        BeanUtils.copyProperties(objectInfoFailure, objectInfoNoPermissions);
        objectInfoNoPermissions.setResult(SimulatorConstants.RESULT_NO_PERMISSION_RESPONSE);

        return objectInfoNoPermissions;
    }

    private static ObjectInfoDto initObjectInfoWithTelAndSpeedDto() {
        var propertiesDto = List.of(new PropertyDto("Телефонный номер", "+79041708083"));
        var sensorsDto = List.of(new SensorDto(null, null, null, "Скорость", "100 км/ч", null, true));

        var objectInfoWithTelAndSpeedDto = new ObjectInfoDto();
        objectInfoWithTelAndSpeedDto.setProperties(propertiesDto);
        objectInfoWithTelAndSpeedDto.setSensors(sensorsDto);
        objectInfoWithTelAndSpeedDto.setOid(SimulatorConstants.ID_OBJECT_INFO_WITH_TEL_AND_SENSORS);
        objectInfoWithTelAndSpeedDto.setName("Транспортная единица " + SimulatorConstants.ID_OBJECT_INFO_WITH_TEL_AND_SENSORS);
        objectInfoWithTelAndSpeedDto.setImei("IMEI" + SimulatorConstants.ID_OBJECT_INFO_WITH_TEL_AND_SENSORS);
        objectInfoWithTelAndSpeedDto.setCid(null);
        objectInfoWithTelAndSpeedDto.setDt(Instant.now());
        objectInfoWithTelAndSpeedDto.setProperties(propertiesDto);
        objectInfoWithTelAndSpeedDto.setSensors(sensorsDto);
        objectInfoWithTelAndSpeedDto.setResult(SimulatorConstants.RESULT_SUCCESS_RESPONSE);
        objectInfoWithTelAndSpeedDto.setParentId(530L);
        objectInfoWithTelAndSpeedDto.setAddress(null);
        objectInfoWithTelAndSpeedDto.setObjIcon("");
        objectInfoWithTelAndSpeedDto.setObjIconHeight(0);
        objectInfoWithTelAndSpeedDto.setObjIconWidth(0);
        objectInfoWithTelAndSpeedDto.setObjIconRotate(false);
        objectInfoWithTelAndSpeedDto.setStatus(5);
        objectInfoWithTelAndSpeedDto.setLat(48.61654);
        objectInfoWithTelAndSpeedDto.setLon(42.81458);
        objectInfoWithTelAndSpeedDto.setDirection(207.0);
        objectInfoWithTelAndSpeedDto.setMove(100);
        objectInfoWithTelAndSpeedDto.setBlockReason(0);

        return objectInfoWithTelAndSpeedDto;
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

    @DisplayName("получить дерево объектов")
    @Test
    void shouldCorrectGetTreeSuccess() {
        when(simulatorDao.getTreeSuccess()).thenReturn(TREE_SUCCESS);
        var actualTreeDto = simulatorService.getTreeSuccess(true);

        assertThat(actualTreeDto).isEqualTo(TREE_SUCCESS_DTO);
    }

    @DisplayName("получить пустое дерево объектов")
    @Test
    void shouldCorrectGetTreeCleanSuccess() {
        when(simulatorDao.getTreeCleanSuccess()).thenReturn(TREE_CLEAN);
        var actualTreeDto = simulatorService.getTreeSuccess(false);

        assertThat(actualTreeDto).isEqualTo(TREE_CLEAN_DTO);
    }

    @DisplayName("получить ошибочное дерево объектов")
    @Test
    void shouldCorrectGetTreeFailure() {
        when(simulatorDao.getTreeFailure()).thenReturn(TREE_FAILURE);
        var actualTreeDto = simulatorService.getTreeFailure();

        assertThat(actualTreeDto).isEqualTo(TREE_FAILURE_DTO);
    }

    @DisplayName("получить информацию об объекте")
    @Test
    void shouldCorrectObjectInfoSuccess() {
        when(simulatorDao.getObjectInfoSuccess(ID_OBJECT_INFO_SUCCESS)).thenReturn(OBJECT_INFO_SUCCESS);
        var actualObjectInfoDto = simulatorService.getObjectInfoSuccess(ID_OBJECT_INFO_SUCCESS);

        assertThat(actualObjectInfoDto).usingRecursiveComparison()
                                       .ignoringFields("dt")
                                       .isEqualTo(OBJECT_INFO_SUCCESS_DTO);
    }

    @DisplayName("получить информацию об объекте с телефоном и скоростью")
    @Test
    void shouldCorrectObjectInfoSuccessWithTelAndSpeed() {
        when(simulatorDao.getObjectInfoWithTelAndSpeed()).thenReturn(OBJECT_INFO_WITH_TEL_AND_SPEED);
        var actualObjectInfoDto = simulatorService.getObjectInfoSuccess(SimulatorConstants.ID_OBJECT_INFO_WITH_TEL_AND_SENSORS);

        assertThat(actualObjectInfoDto).usingRecursiveComparison()
                                       .ignoringFields("dt")
                                       .isEqualTo(OBJECT_INFO_WITH_TEL_AND_SPEED_DTO);
    }

    @DisplayName("получить информацию об объекте с несуществующим идентификатором")
    @Test
    void shouldCorrectObjectInfoNoPermission() {
        when(simulatorDao.getObjectInfoSuccess(ID_OBJECT_INFO_NO_PERMISSION)).thenReturn(OBJECT_INFO_NO_PERMISSIONS);
        var actualObjectInfoDto = simulatorService.getObjectInfoSuccess(ID_OBJECT_INFO_NO_PERMISSION);

        assertThat(actualObjectInfoDto).isEqualTo(OBJECT_INFO_NO_PERMISSIONS_DTO);
    }

    @DisplayName("получить ошибочную информацию об объекте")
    @Test
    void shouldCorrectGetObjectInfoFailure() {
        when(simulatorDao.getObjectInfoFailure()).thenReturn(OBJECT_INFO_FAILURE);
        var actualObjectInfoDto = simulatorService.getObjectInfoFailure();

        assertThat(actualObjectInfoDto).isEqualTo(OBJECT_INFO_FAILURE_DTO);
    }

}