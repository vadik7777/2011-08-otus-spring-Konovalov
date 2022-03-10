package ru.otus.project.rnis.simulator.constants;

public interface SimulatorConstants {

    String SECURE_COOKIE = "SGUID";

    String CONNECT_RESPONSE_SUCCESS_BODY = "\"Ok\"";
    String CONNECT_RESPONSE_FAILURE_BODY = "Неверный логин или пароль.";

    String PING_RESPONSE_SUCCESS_BODY = "true";
    String PING_RESPONSE_FAILURE_BODY = "false";

    String RESULT_SUCCESS_RESPONSE = "Ok";
    String RESULT_FAILURE_RESPONSE = "NoAuth";
    String RESULT_NO_PERMISSION_RESPONSE = "PermissionsNotEnough";

    Long ID_OBJECT_INFO_WITH_TEL_AND_SENSORS = 1712L;

}