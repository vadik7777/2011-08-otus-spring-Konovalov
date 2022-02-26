package ru.otus.project.rnis.service;

import org.springframework.http.ResponseEntity;
import ru.otus.project.rnis.dto.rnis.ObjectInfoDto;
import ru.otus.project.rnis.dto.rnis.TreeDto;

public interface RnisService {

    ResponseEntity<String> ping();

    ObjectInfoDto getObjectInfo(Long oid);

    TreeDto getTree();
}