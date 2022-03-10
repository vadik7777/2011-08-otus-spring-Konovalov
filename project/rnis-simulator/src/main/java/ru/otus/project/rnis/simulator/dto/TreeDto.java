package ru.otus.project.rnis.simulator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TreeDto {
    private List<TreeChildrenDto> children;  // Список групп верхнего уровня
    private String result;                   // Результат выполнения запроса. "Ok" - если запрос выполнен успешно, или текст ошибки
}