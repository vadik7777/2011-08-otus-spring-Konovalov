package ru.otus.project.rnis.simulator.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import ru.otus.project.rnis.simulator.domain.ObjectInfo;
import ru.otus.project.rnis.simulator.domain.TreeChildren;
import ru.otus.project.rnis.simulator.dto.ObjectInfoDto;
import ru.otus.project.rnis.simulator.constants.SimulatorConstants;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Component
public class ObjectInfoMapper {

    private final ModelMapper modelMapper;

    @PostConstruct
    private void init() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.createTypeMap(ObjectInfo.class, ObjectInfoDto.class);

        modelMapper.createTypeMap(TreeChildren.class, ObjectInfo.class)
                   .addMapping(TreeChildren::getRealId, ObjectInfo::setOid)
                   .setPostConverter(postConverter());
    }

    public ObjectInfoDto toDto(ObjectInfo objectInfo) {
        return modelMapper.map(objectInfo, ObjectInfoDto.class);
    }

    public ObjectInfo toObjectInfo(TreeChildren treeChildren) {
        return modelMapper.map(treeChildren, ObjectInfo.class);
    }

    private Converter<TreeChildren, ObjectInfo> postConverter() {
        return mappingContext -> {
            var dst = mappingContext.getDestination();
            dst.setResult(SimulatorConstants.RESULT_SUCCESS_RESPONSE);
            return dst;
        };
    }
}