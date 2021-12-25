package ru.otus.homework15.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework15.dto.MatryoshkaDto;
import ru.otus.homework15.mapper.MatryoshkaMapper;
import ru.otus.homework15.repository.MatryoshkaRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MatryoshkaServiceImpl implements MatryoshkaService {

    private final MatryoshkaRepository matryoshkaRepository;
    private final MatryoshkaMapper matryoshkaMapper;

    @Transactional
    @Override
    public MatryoshkaDto create(MatryoshkaDto matryoshkaDto) {
        if (matryoshkaDto.getId() == 0) {
            return matryoshkaMapper.toDto(matryoshkaRepository.save(matryoshkaMapper.toEntity(matryoshkaDto)));
        }
        MatryoshkaDto newMatryoshkaDto = new MatryoshkaDto();
        newMatryoshkaDto.setStep(matryoshkaDto.getStep() + 1);
        newMatryoshkaDto.setMaxSteps(matryoshkaDto.getMaxSteps());
        newMatryoshkaDto.setMatryoshka(matryoshkaDto);
        return matryoshkaMapper.toDto(matryoshkaRepository.save(matryoshkaMapper.toEntity(newMatryoshkaDto)));
    }

    @Transactional(readOnly = true)
    @Override
    public List<MatryoshkaDto> findAll() {
        return matryoshkaRepository.findAll().stream().map(matryoshkaMapper::toDto).collect(Collectors.toList());
    }
}