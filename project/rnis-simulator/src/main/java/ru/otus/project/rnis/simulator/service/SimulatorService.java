package ru.otus.project.rnis.simulator.service;

import ru.otus.project.rnis.simulator.dto.ObjectInfoDto;
import ru.otus.project.rnis.simulator.dto.TreeDto;

public interface SimulatorService {

    ObjectInfoDto getObjectInfoSuccess(Long oid);

    ObjectInfoDto getObjectInfoFailure();

    TreeDto getTreeSuccess(boolean all);

    TreeDto getTreeFailure();

}