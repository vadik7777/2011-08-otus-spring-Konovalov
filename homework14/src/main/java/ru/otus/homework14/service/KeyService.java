package ru.otus.homework14.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class KeyService {

    private final HashMap<Long, String> authorKeys = new HashMap<>();
    private final HashMap<Long, String> genreKeys = new HashMap<>();
    private final HashMap<Long, String> bookKeys = new HashMap<>();

    public void putAuthorKey(long jpaKey, String mongoKey) {
        authorKeys.put(jpaKey, mongoKey);
    }

    public String getAuthorKey(long jpaKey) {
        return authorKeys.get(jpaKey);
    }

    public void putGenreKey(long jpaKey, String mongoKey) {
        genreKeys.put(jpaKey, mongoKey);
    }

    public String getGenreKey(long jpaKey) {
        return genreKeys.get(jpaKey);
    }

    public void putBookKey(long jpaKey, String mongoKey) {
        bookKeys.put(jpaKey, mongoKey);
    }

    public String getBookKey(long jpaKey) {
        return bookKeys.get(jpaKey);
    }
}