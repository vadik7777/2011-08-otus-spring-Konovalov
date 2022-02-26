package ru.otus.project.rnis.simulator.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TreeChildren implements ITree {
    @JsonProperty("IMEI")
    private String imei;                                  // IMEI объекта
    @JsonProperty("IsGroup")
    private Boolean isGroup;                              // Признак что данная запись это группа (true - это группа, false - это объект)
    @JsonProperty("block_reason")
    private Integer blockReason;                          // Причина блокировки объекта. Возможные варианты: 0 - не заблокирован; 1 - заблокирован пользователем(ручная блокировка); 2 - заблокирован за неуплату
    private List<TreeChildren> children;                  // Список объектов или групп входящих в данную группу
    private Double direction;                             // Направление движения объекта в градусах относительно севера
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant dt;                                   // Время последних данных от объект
    private String iconCls;                               // Имя CSS класса для отображения объекта в дереве
    private Long id;                                      // Идентификатор группы или объекта в пределах данного дерева
    private Double lat;                                   // Широта местоположения объекта в формате градусы – доли градусов
    private Boolean leaf;                                 // Признак что данная запись - объект (false - группа, true - объект)
    private Double lon;                                   // Долгота местоположения объекта в формате градусы – доли градусов
    private Integer move;                                 // Текущий статус перемещения объекта. Возможные варианты: 110 - стоянка, связь есть; 111 - движение, связь есть; 101 - нет связи, до потери связи двигался; 100 - нет связи, до потери связи стоял; 200 - в последнем сообщении GPS не валиден, сейчас нет связи, до этого стоял; 201 - в последнем сообщении GPS не валиден, сейчас нет связи, до этого двигался; 210 - GPS не валиден, связь есть, до этого стоял; 211 - GPS не валиден, связь есть, до этого двигался; 666 - Объект заблокирован за неуплату; 667 - Объект заблокирован пользователем(ручная блокировка)
    private String name;                                  // Текстовое имя объекта заданное ему при создании
    @JsonProperty("obj_icon")
    private String objIcon;                               // Имя файла иконки для отображения объекта на карте (все файлы с иконками доступны по адресу [адрес сервера]/icons/objects/[имя файла])
    @JsonProperty("obj_icon_height")
    private Integer objIconHeight;                        // Высота иконки объекта в пикселях
    @JsonProperty("obj_icon_width")
    private Integer objIconWidth;                         // Ширина иконки объекта в пикселях
    @JsonProperty("obj_icon_rotate")
    private Boolean objIconRotate;                        // Признак необходимости поворачивать иконку объекта на карте при изменении направления его движения (true - поворачивать, false - не поворачивать)
    @JsonProperty("parent_id")
    private Long parentId;                                // Идентификатор в базе данных родительской группы объекта
    @JsonProperty("real_id")
    private Long realId;                                  // Идентификатор объекта в базе данных. Именно он должен фигурировать в качестве ID объекта во всех запросах
    private Integer status;                               // Текущий статус объекта (наиболее критичное незакрытое событие). Возможные варианты: 1 - Критичное, 2 - Серьезное, 3 - Несущественное, 4 - Информационное, 5 - Нормальное
}