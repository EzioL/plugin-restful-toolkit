package com.ezio.plugin.utils;

import com.ezio.plugin.navigator.domain.RestServiceItem;
import com.ezio.plugin.navigator.domain.RestServiceProject;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;

import java.util.ArrayList;
import java.util.List;
/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/1/13
 */
public class ServiceUtils {


    public static java.util.List<RestServiceProject> buildRestServiceProjectList(com.intellij.openapi.project.Project project) {

        System.out.println("buildRestServiceProjectList");
        java.util.List<RestServiceProject> serviceProjectList = new ArrayList<>();
        com.intellij.openapi.module.Module[] modules = ModuleManager.getInstance(project).getModules();
        for (com.intellij.openapi.module.Module module : modules) {
            // TODO: Ezio 2020/1/13  添加端口扫描
            List<RestServiceItem> restServices = buildRestServiceItemList(module);
            if (restServices.size() > 0) {
                serviceProjectList.add(new RestServiceProject(RestServiceProject.DEFAULT_PORT, module, restServices));
            }
        }

        return serviceProjectList;

    }

    private static List<RestServiceItem> buildRestServiceItemList(Module module) {

        return new ArrayList<>();
    }
}
