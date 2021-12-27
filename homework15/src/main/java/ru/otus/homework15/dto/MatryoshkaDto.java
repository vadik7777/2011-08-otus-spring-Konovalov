package ru.otus.homework15.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MatryoshkaDto {
    private long id;
    private int step;
    private int maxSteps;
    private MatryoshkaDto matryoshka;

    public MatryoshkaDto(int maxSteps) {
        this.maxSteps = maxSteps;
        this.step = 1;
    }
}