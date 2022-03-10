package ru.otus.project.rnis.converter.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.otus.project.rnis.dto.rest.MunicipalityDto;
import ru.otus.project.rnis.dto.rest.OrganizationDto;
import ru.otus.project.rnis.dto.rest.TransportTypeDto;
import ru.otus.project.rnis.dto.rest.TransportUnitDto;
import ru.otus.project.rnis.dto.rnis.TreeChildrenDto;
import ru.otus.project.rnis.dto.rnis.TreeDto;
import ru.otus.project.rnis.mapper.TreeChildrenMapper;
import ru.otus.project.rnis.converter.TreeConverter;
import ru.otus.project.rnis.converter.impl.util.TreeWalker;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Component
public class TreeConverterImpl implements TreeConverter {

    private final static int TRANSPORT_TYPE_LEVEL = 0;
    private final static int MUNICIPALITY_LEVEL = 1;
    private final static int ORGANIZATION_LEVEL = 2;
    private final static int TRANSPORT_UNIT_LEVEL = 3;

    private final TreeWalker treeWalker;
    private final TreeChildrenMapper treeChildrenMapper;

    @Override
    public List<TransportTypeDto> getTransportTypes(TreeDto treeDto) {
        var transportTypeMap = new ConcurrentHashMap<Long, TreeChildrenDto>();
        treeWalker.treeWalk(TRANSPORT_TYPE_LEVEL, treeDto, transportTypeMap, false);
        var transportTypes = transportTypeMap.values()
                                             .stream()
                                             .map(treeChildrenMapper::toTransportTypeDto)
                                             .collect(Collectors.toList());
        return transportTypes;
    }

    @Override
    public List<MunicipalityDto> getMunicipalities(TreeDto treeDto) {
        var municipalityMap = new ConcurrentHashMap<Long, TreeChildrenDto>();
        treeWalker.treeWalk(MUNICIPALITY_LEVEL, treeDto, municipalityMap, true);
        var municipalities = municipalityMap.values()
                                            .stream()
                                            .map(treeChildrenMapper::toMunicipalityDto)
                                            .collect(Collectors.toList());
        return municipalities;
    }

    @Override
    public List<OrganizationDto> getOrganizations(TreeDto treeDto) {
        var organizationMap = new ConcurrentHashMap<Long, TreeChildrenDto>();
        treeWalker.treeWalk(ORGANIZATION_LEVEL, treeDto, organizationMap, true);
        var organizations = organizationMap.values()
                                           .stream()
                                           .map(treeChildrenMapper::toOrganizationDto)
                                           .collect(Collectors.toList());
        return organizations;
    }

    @Override
    public List<TransportUnitDto> getTransportUnits(TreeDto treeDto) {
        var transportTypeMap = new ConcurrentHashMap<Long, TreeChildrenDto>();
        var municipalityMap = new ConcurrentHashMap<Long, TreeChildrenDto>();
        var organizationMap = new ConcurrentHashMap<Long, TreeChildrenDto>();
        var transportUnitMap = new ConcurrentHashMap<Long, TreeChildrenDto>();

        treeWalker.treeWalk(TRANSPORT_TYPE_LEVEL, treeDto, transportTypeMap, false);
        treeWalker.treeWalk(MUNICIPALITY_LEVEL, treeDto, municipalityMap, true);
        treeWalker.treeWalk(ORGANIZATION_LEVEL, treeDto, organizationMap, true);
        treeWalker.treeWalk(TRANSPORT_UNIT_LEVEL, treeDto, transportUnitMap, true);

        var transportUnits = transportUnitMap.values().stream()
                                             .map(transportUnitTree -> collect(transportUnitTree,
                                                                               transportTypeMap,
                                                                               municipalityMap,
                                                                               organizationMap))
                                             .filter(Objects::nonNull)
                                             .collect(Collectors.toList());
        return transportUnits;
    }

    private TransportUnitDto collect(TreeChildrenDto transportUnitTree,
                                     ConcurrentHashMap<Long, TreeChildrenDto> transportTypeMap,
                                     ConcurrentHashMap<Long, TreeChildrenDto> municipalityMap,
                                     ConcurrentHashMap<Long, TreeChildrenDto> organizationMap) {
        var organizationId = transportUnitTree.getParentId();
        if (Objects.isNull(organizationMap.get(organizationId))) {
            log.error("organization not found, id = {}", transportUnitTree.getRealId());
            return null;
        }
        var organizationTree = organizationMap.get(organizationId);

        var municipalityId = organizationTree.getParentId();
        if (Objects.isNull(municipalityMap.get(municipalityId))) {
            log.error("municipality not found, id = {}", transportUnitTree.getRealId());
            return null;
        }
        var municipalityTree = municipalityMap.get(municipalityId);

        var transportTypeId = municipalityTree.getParentId();
        if (Objects.isNull(transportTypeMap.get(transportTypeId))) {
            log.error("transport type not found, id = {}", transportUnitTree.getRealId());
            return null;
        }
        var transportTypeTree = transportTypeMap.get(transportTypeId);

        return treeChildrenMapper.toTransportUnitDto(transportTypeTree,
                                                     municipalityTree,
                                                     organizationTree,
                                                     transportUnitTree);
    }
}