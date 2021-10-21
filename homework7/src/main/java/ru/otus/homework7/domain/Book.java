package ru.otus.homework7.domain;

import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "comments")
@ToString(exclude = "comments")
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @BatchSize(size = 10)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "book", fetch = FetchType.LAZY)
    private List<Comment> comments;
}