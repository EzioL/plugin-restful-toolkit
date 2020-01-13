package com.ezio.plugin.navigator.action;

import com.ezio.plugin.navigator.component.RestServiceNavigator;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;

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
        RestServiceNavigator servicesNavigator = RestServiceNavigator.getInstance(project);
        servicesNavigator.initComponent();
//        servicesNavigator.scheduleStructureUpdate();
    }
}
