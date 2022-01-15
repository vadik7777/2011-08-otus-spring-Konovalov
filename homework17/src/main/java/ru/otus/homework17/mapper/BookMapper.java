package ru.otus.homework17.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import ru.otus.homework17.domain.Book;
import ru.otus.homework17.dto.BookDto;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Component
public class BookMapper {

    private final ModelMapper modelMapper;

    @PostConstruct
    private void init() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.createTypeMap(Book.class, BookDto.class);
        modelMapper.createTypeMap(BookDto.class, Book.class);
    }

    public BookDto toDto(Book book) {
        return modelMapper.map(book, BookDto.class);
    }

    public Book toEntity(BookDto bookDto) {
        return modelMapper.map(bookDto, Book.class);
    }
}