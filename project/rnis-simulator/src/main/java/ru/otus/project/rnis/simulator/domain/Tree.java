package ru.otus.project.rnis.simulator.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Tree implements ITree {
    private List<TreeChildren> children;     // Список групп верхнего уровня
    private String result;                   // Результат выполнения запроса. "Ok" - если запрос выполнен успешно, или текст ошибки
}