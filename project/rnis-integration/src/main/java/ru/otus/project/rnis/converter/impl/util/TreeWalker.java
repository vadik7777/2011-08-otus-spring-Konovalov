package ru.otus.project.rnis.converter.impl.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.otus.project.rnis.dto.rnis.Tree;
import ru.otus.project.rnis.dto.rnis.TreeChildrenDto;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class TreeWalker {

    public void treeWalk(int level, Tree tree, ConcurrentHashMap<Long, TreeChildrenDto> treeChildrenMap, boolean checkParent) {
        if (Objects.isNull(tree.getChildren())) {
            return;
        }
        if (level == 0) {
            tree.getChildren().forEach(treeChildren -> {
                if (Objects.isNull(treeChildren.getRealId())) {
                    log.error("object have no real id, name = {}", treeChildren.getName());
                    return;
                }
                if (checkParent && Objects.isNull(treeChildren.getParentId())) {
                    log.error("object have no parent id, id = {}", treeChildren.getRealId());
                    return;
                }
                treeChildrenMap.putIfAbsent(treeChildren.getRealId(), treeChildren);
            });
            return;
        }
        int finalLevel = --level;
        tree.getChildren().forEach(treeChildren -> treeWalk(finalLevel, treeChildren, treeChildrenMap, checkParent));
    }
}