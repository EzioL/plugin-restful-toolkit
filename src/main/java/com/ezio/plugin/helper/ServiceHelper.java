package com.ezio.plugin.helper;

import com.ezio.plugin.handler.ModuleHandler;
import com.ezio.plugin.navigator.domain.RestServiceProject;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/1/13
 */
public class ServiceHelper {

    public static List<RestServiceProject> buildRestServiceProjectList(Project project) {

        // 这里 project 也被当做module了 父子关系打平了
        return Stream.of(ModuleManager.getInstance(project).getModules())
                .map(ModuleHandler::handle)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }


}
