package ru.otus.project.rnis.simulator.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Property {
    private String name; // имя параметра
    private String val;  // значение параметра
}