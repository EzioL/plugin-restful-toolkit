package com.ezio.plugin.helper;

import com.ezio.plugin.handler.ModuleHandler;
import com.ezio.plugin.navigator.domain.RestServiceItem;
import com.ezio.plugin.navigator.domain.RestServiceProject;
import com.ezio.plugin.resolver.SpringResolver;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

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


    public static List<RestServiceItem> buildRestServiceItemList(Module module) {
        SpringResolver springResolver = new SpringResolver(module);
        return springResolver.findAllSupportedServiceItemsInModule();
    }


    @NotNull
    public static List<RestServiceItem> buildRestServiceItemList(Project project) {

        SpringResolver springResolver = new SpringResolver(project);
        return springResolver.findAllSupportedServiceItemsInProject();
    }

}
