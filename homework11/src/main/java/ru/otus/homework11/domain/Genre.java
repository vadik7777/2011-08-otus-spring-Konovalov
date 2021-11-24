package ru.otus.homework11.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Genre {
    @Id
    private String id;

    private String name;

    public Genre(String name) {
        this.name = name;
    }
}