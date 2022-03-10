package ru.otus.project.rnis.simulator.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Sensor {
    private Long sid;                                    // уникальный идентификатор датчика в пределах всех датчииков объекта
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant dt;                                  // время и дата в UTC (формат yyyy-MM-dd HH:mm:ss) получения сообщения от объекта с данными по этому датчику (не все сообщения от терминалов включают данные по всем датчикам)
    private String ico;                                  // имя файла изображения для отображения иконки датчика (все файлы с иконками доступны по адресу http://[DNS имя или IP адрес сервера]/icons/[имя файла])
    private String name;                                 // имя датчика
    private String val;                                  // значение датчика с указанием единиц измерения, если необходимо
    private Integer st;                                  // статус датчика(наиболее критичное незакрытое событие по датчику). Возможные варианты:, 1 - Критичное, 2 - Серьезное, 3 - Несущественное, 4 - Информационное, 5 - Нормальное
    private Boolean haveChart;                           // признак того что по данному датчику можно запросить график
}