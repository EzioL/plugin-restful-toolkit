package java.com.ezio.plugin.utils;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;

import java.com.ezio.plugin.navigator.domain.RestServiceItem;
import java.com.ezio.plugin.navigator.domain.RestServiceProject;
import java.util.ArrayList;
import java.util.List;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/1/13
 */
public class ServiceUtils {


    public static List<RestServiceProject> buildRestServiceProjectList(Project project) {

        System.out.println("buildRestServiceProjectList");
        List<RestServiceProject> serviceProjectList = new ArrayList<>();
        Module[] modules = ModuleManager.getInstance(project).getModules();
        for (Module module : modules) {
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
