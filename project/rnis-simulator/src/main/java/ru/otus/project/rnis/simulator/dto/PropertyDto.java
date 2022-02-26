package ru.otus.project.rnis.simulator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PropertyDto {
    private String name; // имя параметра
    private String val;  // значение параметра
}