package com.ezio.plugin.navigator.component;

import com.ezio.plugin.helper.ServiceHelper;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import com.ezio.plugin.navigator.domain.RestServiceProject;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/1/13
 */
public class RestServiceProjectManager implements com.intellij.openapi.components.ProjectComponent {

    protected final com.intellij.openapi.project.Project project;

    public RestServiceProjectManager(com.intellij.openapi.project.Project project) {
        super();
        this.project = project;
    }

    public static RestServiceProjectManager getInstance(@NotNull Project project) {
        return ServiceManager.getService(project, RestServiceProjectManager.class);
    }

    public java.util.List<RestServiceProject> getServiceProjectList() {
        return DumbService.getInstance(project).runReadActionInSmartMode(() ->
                ServiceHelper.buildRestServiceProjectList(project));
    }


}
