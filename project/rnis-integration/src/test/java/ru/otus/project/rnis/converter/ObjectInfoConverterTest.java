package ru.otus.project.rnis.converter;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.project.rnis.dto.rest.NavigationInformationDto;
import ru.otus.project.rnis.dto.rest.TransportUnitDto;
import ru.otus.project.rnis.dto.rnis.ObjectInfoDto;
import ru.otus.project.rnis.dto.rnis.PropertyDto;
import ru.otus.project.rnis.dto.rnis.SensorDto;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.project.rnis.constants.RnisConstants.ID_OBJECT_INFO_WITH_TEL_AND_SENSORS;
import static ru.otus.project.rnis.constants.RnisConstants.RNIS_RESULT_SUCCESS_RESPONSE;

@DisplayName("Конвертер для работы с информацией об объекте должен")
@SpringBootTest
class ObjectInfoConverterTest {

    @Autowired
    private ObjectInfoConverter objectInfoConverter;

    private final static Long ID_TRANSPORT_UNIT = 1L;
    private final static TransportUnitDto TRANSPORT_UNIT_DTO = initTUDto();
    private final static TransportUnitDto TRANSPORT_UNIT_WITH_TEL_AND_SPEED_DTO = initTUWithTelAndSpeedDto();
    private final static NavigationInformationDto NAVIGATION_INFORMATION_DTO = initNIDto();
    private final static NavigationInformationDto NAVIGATION_INFORMATION_WITH_SPEED_DTO = initNIWithSpeedDto();
    private final static String[] NAVIGATION_INFORMATION_IGNORE_FIELDS = {"id"};

    private final static ObjectInfoDto OBJECT_INFO_DTO = initObjectInfoDto();
    private final static ObjectInfoDto OBJECT_INFO_DTO_WITH_TELS_AND_SENSORS_DTO = initObjectInfoWithTelsAndSensorsDto();

    private static TransportUnitDto initTUDto() {
        return new TransportUnitDto(ID_TRANSPORT_UNIT,
                                    "Транспортная единица 1",
                                    null,
                                    Instant.parse("2022-02-14T03:44:26Z"),
                                    44.51461,
                                    48.75955,
                                    null,
                                    207.0,
                                    null,
                                    null,
                                    null);
    }

    private static TransportUnitDto initTUWithTelAndSpeedDto() {
        return new TransportUnitDto(ID_OBJECT_INFO_WITH_TEL_AND_SENSORS,
                                    "Транспортная единица 1712",
                                    "+79041708083",
                                    Instant.parse("2022-02-14T03:44:26Z"),
                                    42.81458,
                                    48.61654,
                                    "100 км/ч",
                                    207.0,
                                    null,
                                    null,
                                    null);
    }

    private static NavigationInformationDto initNIDto() {
        return new NavigationInformationDto(null,
                                            Instant.parse("2022-02-14T03:44:26Z"),
                                            44.51461,
                                            48.75955,
                                            null,
                                            207.0,
                                            new TransportUnitDto(ID_TRANSPORT_UNIT));
    }

    private static NavigationInformationDto initNIWithSpeedDto() {
        return new NavigationInformationDto(null,
                                            Instant.parse("2022-02-14T03:44:26Z"),
                                            42.81458,
                                            48.61654,
                                            "100 км/ч",
                                            207.0,
                                            new TransportUnitDto(ID_OBJECT_INFO_WITH_TEL_AND_SENSORS));
    }

    private static ObjectInfoDto initObjectInfoDto() {
        return new ObjectInfoDto(ID_TRANSPORT_UNIT,
                                 "Транспортная единица " + ID_TRANSPORT_UNIT,
                                 "IMEI" + ID_TRANSPORT_UNIT,
                                 null,
                                 Instant.parse("2022-02-14T03:44:26Z"),
                                 null,
                                 null,
                                 RNIS_RESULT_SUCCESS_RESPONSE,
                                 6L,
                                 null,
                                 "",
                                 0,
                                 0,
                                 false,
                                 5,
                                 48.75955,
                                 44.51461,
                                 207.0,
                                 110,
                                 0);
    }

    private final static ObjectInfoDto initObjectInfoWithTelsAndSensorsDto() {
        return new ObjectInfoDto(ID_OBJECT_INFO_WITH_TEL_AND_SENSORS,
                                 "Транспортная единица " + ID_OBJECT_INFO_WITH_TEL_AND_SENSORS,
                                 "IMEI" + ID_OBJECT_INFO_WITH_TEL_AND_SENSORS,
                                 null,
                                 Instant.parse("2022-02-14T03:44:26Z"),
                                 List.of(new PropertyDto("Телефонный номер", "+79041708083")),
                                 List.of(new SensorDto(null, null, null, "Скорость", "100 км/ч", null, true)),
                                 RNIS_RESULT_SUCCESS_RESPONSE,
                                 530L,
                                 null,
                                 "",
                                 0,
                                 0,
                                 false,
                                 5,
                                 48.61654,
                                 42.81458,
                                 207.0,
                                 110,
                                 0);
    }

    @DisplayName("получать траспортную единицу")
    @Test
    void shouldCorrectGetTransportUnit() {
        var actualTransportUnitDto = objectInfoConverter.getTransportUnit(OBJECT_INFO_DTO);

        assertThat(actualTransportUnitDto).usingRecursiveComparison().isEqualTo(TRANSPORT_UNIT_DTO);
    }

    @DisplayName("получать траспортную единицу c телефоном и скоростью")
    @Test
    void shouldCorrectGetTransportUnitWithTelAndSpeed() {
        var actualTransportUnitDto = objectInfoConverter.getTransportUnit(OBJECT_INFO_DTO_WITH_TELS_AND_SENSORS_DTO);

        assertThat(actualTransportUnitDto).usingRecursiveComparison().isEqualTo(TRANSPORT_UNIT_WITH_TEL_AND_SPEED_DTO);
    }

    @DisplayName("получать навигационную информацию")
    @Test
    void shouldCorrectGetNavigationInformation() {
        var actualNavigationInformationDto = objectInfoConverter.getNavigationInformation(OBJECT_INFO_DTO);

        assertThat(actualNavigationInformationDto).usingRecursiveComparison()
                                                  .ignoringFields(NAVIGATION_INFORMATION_IGNORE_FIELDS)
                                                  .isEqualTo(NAVIGATION_INFORMATION_DTO);
    }

    @DisplayName("получать навигационную информацию со скоростью")
    @Test
    void shouldCorrectGetNavigationInformationWithSpeed() {
        var actualNavigationInformationDto =
                objectInfoConverter.getNavigationInformation(OBJECT_INFO_DTO_WITH_TELS_AND_SENSORS_DTO);

        assertThat(actualNavigationInformationDto).usingRecursiveComparison()
                                                  .ignoringFields(NAVIGATION_INFORMATION_IGNORE_FIELDS)
                                                  .isEqualTo(NAVIGATION_INFORMATION_WITH_SPEED_DTO);
    }
}