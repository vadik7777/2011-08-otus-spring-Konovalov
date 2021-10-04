package ru.otus.homework5.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.homework5.dao.BookDao;
import ru.otus.homework5.domain.Author;
import ru.otus.homework5.domain.Book;
import ru.otus.homework5.domain.Genre;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService{

    private final BookDao bookDao;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Override
    public Book insert(Book book) {

        book.setAuthor(getAuthorAndInsertIfNotExistsByName(book.getAuthor()));
        book.setGenre(getGenreAndInsertIfNotExistsByName(book.getGenre()));

        return bookDao.insert(book);
    }

    @Override
    public Book update(Book book) {

        book.setAuthor(getAuthorAndInsertIfNotExistsByName(book.getAuthor()));
        book.setGenre(getGenreAndInsertIfNotExistsByName(book.getGenre()));

        return bookDao.update(book);
    }

    @Override
    public Book getById(long id) {
        return bookDao.getById(id);
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Override
    public void deleteById(long id) {
        bookDao.deleteById(id);
    }

    private Author getAuthorAndInsertIfNotExistsByName(Author author) {
        if (author.getId() == 0 && author.getName() == null) {
            throw new RuntimeException("Author does not have fields: " + author);
        } else if (author.getId() != 0 && author.getName() != null) {
            return authorService.getByIdAndName(author.getId(), author.getName());
        } else if (author.getId() != 0) {
            return authorService.getById(author.getId());
        } else {
            try {
                return authorService.getByName(author.getName());
            } catch (EmptyResultDataAccessException e) {
                return authorService.insertWithOutId(author);
            }
        }
    }

    private Genre getGenreAndInsertIfNotExistsByName(Genre genre) {
        if (genre.getId() == 0 && genre.getName() == null) {
            throw new RuntimeException("Genre does not have fields: " + genre);
        } else if (genre.getId() != 0 && genre.getName() != null) {
            return genreService.getByIdAndName(genre.getId(), genre.getName());
        } else if (genre.getId() != 0) {
            return genreService.getById(genre.getId());
        } else {
            try {
                return genreService.getByName(genre.getName());
            } catch (EmptyResultDataAccessException e) {
                return genreService.insertWithOutId(genre);
            }
        }
    }
}