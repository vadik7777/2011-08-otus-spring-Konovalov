package ru.otus.project.rnis.simulator.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.project.rnis.simulator.dao.SimulatorDao;
import ru.otus.project.rnis.simulator.dto.ObjectInfoDto;
import ru.otus.project.rnis.simulator.dto.TreeDto;
import ru.otus.project.rnis.simulator.mapper.ObjectInfoMapper;
import ru.otus.project.rnis.simulator.mapper.TreeMapper;
import ru.otus.project.rnis.simulator.service.SimulatorService;
import ru.otus.project.rnis.simulator.constants.SimulatorConstants;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class SimulatorServiceImpl implements SimulatorService {

    private final SimulatorDao simulatorDao;
    private final ObjectInfoMapper objectInfoMapper;
    private final TreeMapper treeMapper;

    @Override
    public ObjectInfoDto getObjectInfoSuccess(Long oid) {
        if (Objects.equals(SimulatorConstants.ID_OBJECT_INFO_WITH_TEL_AND_SENSORS, oid)) {
            return objectInfoMapper.toDto(simulatorDao.getObjectInfoWithTelAndSpeed());
        }
        return objectInfoMapper.toDto(simulatorDao.getObjectInfoSuccess(oid));
    }

    @Override
    public ObjectInfoDto getObjectInfoFailure() {
        return objectInfoMapper.toDto(simulatorDao.getObjectInfoFailure());
    }

    @Override
    public TreeDto getTreeSuccess(boolean all) {
        if (all) {
            return treeMapper.toDto(simulatorDao.getTreeSuccess());
        }
        return treeMapper.toDto(simulatorDao.getTreeCleanSuccess());
    }

    @Override
    public TreeDto getTreeFailure() {
        return treeMapper.toDto(simulatorDao.getTreeFailure());
    }
}