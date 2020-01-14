package com.ezio.plugin.helper;

import com.ezio.plugin.navigator.domain.RestServiceItem;
import com.ezio.plugin.navigator.domain.RestServiceProject;
import com.ezio.plugin.resolver.SpringResolver;
import com.intellij.openapi.diagnostic.Logger;
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
public class ServiceHelper {

    public static final Logger LOG = Logger.getInstance(ServiceHelper.class);


    public static List<RestServiceProject> buildRestServiceProjectList(com.intellij.openapi.project.Project project) {

        List<RestServiceProject> serviceProjectList = new ArrayList<>();
        // 这里 project 也被当做module了 父子关系打平了
        Module[] modules = ModuleManager.getInstance(project).getModules();
        // TODO: Ezio 2020/1/13  搞一个处理module的类  端口，方法扫描
        for (Module module : modules) {

            List<RestServiceItem> restServices = buildRestServiceItemList(module);
            if (restServices.size() > 0) {
                serviceProjectList.add(new RestServiceProject(RestServiceProject.DEFAULT_PORT, module, restServices));
            }
        }

        return serviceProjectList;

    }

    private static List<RestServiceItem> buildRestServiceItemList(Module module) {
        SpringResolver springResolver = new SpringResolver(module);
        return springResolver.findAllSupportedServiceItemsInModule();
    }
}
