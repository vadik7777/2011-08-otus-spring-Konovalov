package ru.otus.project.rnis.converter;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.project.rnis.dto.rest.NavigationInformationDto;
import ru.otus.project.rnis.dto.rest.TransportUnitDto;
import ru.otus.project.rnis.dto.rnis.ObjectInfoDto;
import ru.otus.project.rnis.service.RnisService;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.project.rnis.constants.RnisConstants.ID_OBJECT_INFO_WITH_TEL_AND_SENSORS;

@DisplayName("Конвертер для работы с информацией об объекте должен")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class ObjectInfoConverterTest {

    @Autowired
    private ObjectInfoConverter objectInfoConverter;

    @Autowired
    private RnisService rnisService;

    private final static Long ID_TRANSPORT_UNIT = 1L;
    private final static TransportUnitDto TRANSPORT_UNIT_DTO = initTUDto();
    private final static TransportUnitDto TRANSPORT_UNIT_WITH_TEL_AND_SPEED_DTO = initTUWithTelAndSpeedDto();
    private final static String[] TRANSPORT_UNIT_IGNORE_FIELDS = {"informationDate"};
    private final static NavigationInformationDto NAVIGATION_INFORMATION_DTO = initNIDto();
    private final static NavigationInformationDto NAVIGATION_INFORMATION_WITH_SPEED_DTO = initNIWithSpeedDto();
    private final static String[] NAVIGATION_INFORMATION_IGNORE_FIELDS = {"id", "informationDate"};

    private ObjectInfoDto objectInfoDto;
    private ObjectInfoDto objectInfoWithTelAndSensorsDto;

    private static TransportUnitDto initTUDto() {
        return new TransportUnitDto(ID_TRANSPORT_UNIT,
                                    "Транспортная единица 1",
                                    null,
                                    null,
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
                                    null,
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
                                            null,
                                            44.51461,
                                            48.75955,
                                            null,
                                            207.0,
                                            new TransportUnitDto(ID_TRANSPORT_UNIT));
    }

    private static NavigationInformationDto initNIWithSpeedDto() {
        return new NavigationInformationDto(null,
                                            null,
                                            42.81458,
                                            48.61654,
                                            "100 км/ч",
                                            207.0,
                                            new TransportUnitDto(ID_OBJECT_INFO_WITH_TEL_AND_SENSORS));
    }

    @BeforeAll
    void init() {
        objectInfoDto = rnisService.getObjectInfo(ID_TRANSPORT_UNIT);
        objectInfoWithTelAndSensorsDto = rnisService.getObjectInfo(ID_OBJECT_INFO_WITH_TEL_AND_SENSORS);
    }

    @DisplayName("получать траспортную единицу")
    @Test
    void shouldCorrectGetTransportUnit() {
        var actualTransportUnitDto = objectInfoConverter.getTransportUnit(objectInfoDto);

        assertThat(actualTransportUnitDto).usingRecursiveComparison()
                                          .ignoringFields(TRANSPORT_UNIT_IGNORE_FIELDS)
                                          .isEqualTo(TRANSPORT_UNIT_DTO);
    }

    @DisplayName("получать траспортную единицу c телефоном и скоростью")
    @Test
    void shouldCorrectGetTransportUnitWithTelAndSpeed() {
        var actualTransportUnitDto = objectInfoConverter.getTransportUnit(objectInfoWithTelAndSensorsDto);

        assertThat(actualTransportUnitDto).usingRecursiveComparison()
                                          .ignoringFields(TRANSPORT_UNIT_IGNORE_FIELDS)
                                          .isEqualTo(TRANSPORT_UNIT_WITH_TEL_AND_SPEED_DTO);
    }

    @DisplayName("получать навигационную информацию")
    @Test
    void shouldCorrectGetNavigationInformation() {
        var actualNavigationInformationDto = objectInfoConverter.getNavigationInformation(objectInfoDto);

        assertThat(actualNavigationInformationDto).usingRecursiveComparison()
                                                  .ignoringFields(NAVIGATION_INFORMATION_IGNORE_FIELDS)
                                                  .isEqualTo(NAVIGATION_INFORMATION_DTO);
    }

    @DisplayName("получать навигационную информацию со скоростью")
    @Test
    void shouldCorrectGetNavigationInformationWithSpeed() {
        var actualNavigationInformationDto = objectInfoConverter.getNavigationInformation(objectInfoWithTelAndSensorsDto);

        assertThat(actualNavigationInformationDto).usingRecursiveComparison()
                                                  .ignoringFields(NAVIGATION_INFORMATION_IGNORE_FIELDS)
                                                  .isEqualTo(NAVIGATION_INFORMATION_WITH_SPEED_DTO);
    }
}