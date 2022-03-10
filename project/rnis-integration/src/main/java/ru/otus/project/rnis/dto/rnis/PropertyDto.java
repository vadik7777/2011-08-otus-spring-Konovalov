package ru.otus.project.rnis.dto.rnis;

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