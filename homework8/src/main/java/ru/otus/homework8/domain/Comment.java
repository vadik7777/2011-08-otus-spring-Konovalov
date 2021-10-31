package ru.otus.homework8.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Comment {
    @Id
    private String id;

    private String comment;
    private Book book;

    public Comment(String comment, Book book) {
        this.comment = comment;
        this.book = book;
    }
}
