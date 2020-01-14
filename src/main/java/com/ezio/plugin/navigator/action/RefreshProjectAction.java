package com.ezio.plugin.navigator.action;

import com.ezio.plugin.helper.ServiceHelper;
import com.ezio.plugin.navigator.domain.RestServiceProject;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;

import java.util.List;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/1/13
 */
public class RefreshProjectAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        Project project = CommonDataKeys.PROJECT.getData(e.getDataContext());

        assert project != null;


        List<RestServiceProject> serviceProjectList = getServiceProjectList(project);

        System.out.println(serviceProjectList);
    }



    public List<RestServiceProject> getServiceProjectList(Project project ) {
        return DumbService.getInstance(project).runReadActionInSmartMode(() ->
                ServiceHelper.buildRestServiceProjectList(project));
    }
}
