package com.ezio.plugin.navigator.component;

import com.ezio.plugin.helper.ServiceHelper;
import com.ezio.plugin.navigator.domain.RestServiceProject;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/1/13
 */
public class RestServiceProjectManager implements ProjectComponent , Disposable {

    protected final Project project;

    public RestServiceProjectManager(Project project) {
        super();
        this.project = project;
    }

    public static RestServiceProjectManager getInstance(@NotNull Project project) {
        return ServiceManager.getService(project, RestServiceProjectManager.class);
    }

    public List<RestServiceProject> getServiceProjectList() {
        return DumbService.getInstance(project).runReadActionInSmartMode(() ->
                ServiceHelper.buildRestServiceProjectList(project));
    }


    @Override
    public void dispose() {

    }
}
