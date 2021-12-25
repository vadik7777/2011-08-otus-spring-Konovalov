package ru.otus.homework15.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;;
import ru.otus.homework15.domain.Matryoshka;
import ru.otus.homework15.dto.MatryoshkaDto;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Component
public class MatryoshkaMapper {

    private final ModelMapper modelMapper;

    @PostConstruct
    private void init() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.createTypeMap(Matryoshka.class, MatryoshkaDto.class);
        modelMapper.createTypeMap(MatryoshkaDto.class, Matryoshka.class);
    }

    public MatryoshkaDto toDto(Matryoshka matryoshka) {
        return modelMapper.map(matryoshka, MatryoshkaDto.class);
    }

    public Matryoshka toEntity(MatryoshkaDto matryoshkaDto) {
        return modelMapper.map(matryoshkaDto, Matryoshka.class);
    }
}