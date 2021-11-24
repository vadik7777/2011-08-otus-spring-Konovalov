package ru.otus.homework11.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.homework11.domain.Author;
import ru.otus.homework11.domain.Book;
import ru.otus.homework11.domain.Comment;
import ru.otus.homework11.domain.Genre;
import ru.otus.homework11.repository.mongo.AuthorRepository;
import ru.otus.homework11.repository.mongo.BookRepository;
import ru.otus.homework11.repository.mongo.CommentRepository;
import ru.otus.homework11.repository.mongo.GenreRepository;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private Book book1;
    private Book book2;
    private Book book3;

    @ChangeSet(order = "000", id = "dropDB", author = "v.konovalov", runAlways = true)
    public void dropDB(MongoDatabase database){
        database.drop();
    }

    @ChangeSet(order = "001", id = "initBooks", author = "v.konovalov", runAlways = true)
    public void initBooks(BookRepository bookRepository, AuthorRepository authorRepository,
                          GenreRepository genreRepository) {
        Author author1 = new Author("id_author1", "author1");
        authorRepository.save(author1);
        Genre genre1 = new Genre("id_genre1", "genre1");
        genreRepository.save(genre1);
        book1 = new Book("id_book1", "book1", author1, genre1);
        bookRepository.save(book1);

        Author author2 = new Author("id_author2", "author2");
        authorRepository.save(author2);
        Genre genre2 = new Genre("id_genre2", "genre2");
        genreRepository.save(genre2);
        book2 = new Book("id_book2", "book2", author2, genre2);
        bookRepository.save(book2);

        Author author3 = new Author("id_author3", "author3");
        authorRepository.save(author3);
        Genre genre3 = new Genre("id_genre3", "genre3");
        genreRepository.save(genre3);
        book3 = new Book("id_book3", "book3", author3, genre3);
        bookRepository.save(book3);
    }

    @ChangeSet(order = "002", id = "initComments", author = "v.konovalov", runAlways = true)
    public void initComments(CommentRepository commenRepository) {
        commenRepository.save(new Comment("id_comment1", "comment1", book1));
        commenRepository.save(new Comment("id_comment2", "comment2", book1));
        commenRepository.save(new Comment("id_comment3", "comment3", book2));
        commenRepository.save(new Comment("id_comment4", "comment4", book3));
    }
}