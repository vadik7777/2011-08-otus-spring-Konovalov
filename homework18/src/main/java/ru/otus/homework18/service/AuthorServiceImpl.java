package ru.otus.homework18.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework18.mapper.AuthorMapper;
import ru.otus.homework18.repository.AuthorRepository;
import ru.otus.homework18.dto.AuthorDto;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @HystrixCommand(groupKey = "author", commandKey = "findAllAuthor", fallbackMethod = "findAllFallbackMethod")
    @Transactional(readOnly = true)
    @Override
    public List<AuthorDto> findAll() {
        return authorRepository.findAll().stream().map(authorMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<AuthorDto> findAllFallbackMethod() {
        return List.of();
    }
}