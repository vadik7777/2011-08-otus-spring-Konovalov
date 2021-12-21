package ru.otus.homework14.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import ru.otus.homework14.domain.GenreJpa;
import ru.otus.homework14.domain.GenreMongo;

@RequiredArgsConstructor
@Service
public class GenreService {

    private final KeyService keyService;

    public GenreMongo convert(GenreJpa genreJpa) {
        var objectId = new ObjectId().toString();

        var genreMongo = new GenreMongo(objectId, genreJpa.getName());
        keyService.putGenreKey(genreJpa.getId(), genreMongo.getId());

        return genreMongo;
    }
}