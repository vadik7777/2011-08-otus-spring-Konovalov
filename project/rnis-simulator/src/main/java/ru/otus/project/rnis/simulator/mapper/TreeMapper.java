package ru.otus.project.rnis.simulator.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import ru.otus.project.rnis.simulator.domain.Tree;
import ru.otus.project.rnis.simulator.dto.TreeDto;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Component
public class TreeMapper {

    private final ModelMapper modelMapper;

    @PostConstruct
    private void init() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.createTypeMap(Tree.class, TreeDto.class);
    }

    public TreeDto toDto(Tree tree) {
        return modelMapper.map(tree, TreeDto.class);
    }
}