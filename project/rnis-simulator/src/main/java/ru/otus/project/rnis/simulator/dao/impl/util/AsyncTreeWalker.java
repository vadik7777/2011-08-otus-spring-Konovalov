package ru.otus.project.rnis.simulator.dao.impl.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import ru.otus.project.rnis.simulator.domain.ITree;
import ru.otus.project.rnis.simulator.mapper.ObjectInfoMapper;

import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@EnableAsync
@Component
public class AsyncTreeWalker {

    private final ObjectInfoMapper objectInfoMapper;

    @Async("threadPoolTaskExecutor")
    public void treeWalk(int level, ITree tree, Map objectInfoMap) {
        if (Objects.isNull(tree.getChildren())) {
            return;
        }
        if (level == 0) {
            tree.getChildren().forEach(treeChildren -> {
                if (Objects.nonNull(treeChildren.getRealId())) {
                    objectInfoMap.putIfAbsent(treeChildren.getRealId(), objectInfoMapper.toObjectInfo(treeChildren));
                } else {
                    log.error("object have no real id, name = {}", treeChildren.getRealId());
                }
            });
            return;
        }
        int finalLevel = --level;
        tree.getChildren().forEach(treeChildren -> treeWalk(finalLevel, treeChildren, objectInfoMap));
    }
}