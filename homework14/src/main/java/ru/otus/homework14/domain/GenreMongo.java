package ru.otus.homework14.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class GenreMongo {
    @Id
    private String id;

    private String name;
}