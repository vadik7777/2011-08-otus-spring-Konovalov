package ru.otus.project.rnis.constants;

public interface RnisConstants {

    String HEADER_COOKIE = "Cookie";

    String RNIS_SECURE_COOKIE = "SGUID";
    String RNIS_PING_RESPONSE_SUCCESS_BODY = "true";
    String RNIS_CONNECT_RESPONSE_SUCCESS_BODY = "\"Ok\"";
    String RNIS_AUTHENTICATION_ERROR_MESSAGE = "Authentication RNIS failed, check user and password";
    String RNIS_RESULT_SUCCESS_RESPONSE = "Ok";

    Long ID_OBJECT_INFO_WITH_TEL_AND_SENSORS = 1712L;
    Long ID_WRONG_OBJECT_INFO = 0L;
    String PROPERTY_PHONE_LABEL = "Телефонный номер";
    String SENSOR_SPEED_LABEL = "Скорость";
}