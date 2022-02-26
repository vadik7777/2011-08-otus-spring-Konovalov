package ru.otus.project.rnis.simulator.dao;

import ru.otus.project.rnis.simulator.domain.ObjectInfo;
import ru.otus.project.rnis.simulator.domain.Tree;

public interface SimulatorDao {

    ObjectInfo getObjectInfoSuccess(Long oid);

    ObjectInfo getObjectInfoWithTelAndSpeed();

    ObjectInfo getObjectInfoFailure();

    Tree getTreeSuccess();

    Tree getTreeCleanSuccess();

    Tree getTreeFailure();

}