package ru.otus.homework14.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import ru.otus.homework14.domain.*;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final KeyService keyService;

    public CommentMongo convert(CommentJpa commentJpa) {
        var objectId = new ObjectId().toString();

        var authorKey =  keyService.getAuthorKey(commentJpa.getBook().getAuthor().getId());
        var authorMongo = new AuthorMongo(authorKey, commentJpa.getBook().getAuthor().getName());

        var genreKey =  keyService.getGenreKey(commentJpa.getBook().getGenre().getId());
        var genreMongo = new GenreMongo(genreKey, commentJpa.getBook().getGenre().getName());

        var bookKey = keyService.getBookKey(commentJpa.getBook().getId());
        var bookMongo = new BookMongo(bookKey, commentJpa.getBook().getName(), authorMongo, genreMongo);

        var commentMongo = new CommentMongo(objectId, commentJpa.getComment(), bookMongo);

        return commentMongo;
    }
}