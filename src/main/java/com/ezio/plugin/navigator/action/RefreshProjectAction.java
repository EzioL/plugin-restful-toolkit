package com.ezio.plugin.navigator.action;

import com.ezio.plugin.navigator.component.RestApiNavigator;
import com.ezio.plugin.utils.LogUtils;
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
        Project project = CommonDataKeys.PROJECT.getData(e.getDataContext());
        assert project != null;
        RestApiNavigator restApiNavigator = RestApiNavigator.getInstance(project);
        restApiNavigator.scheduleStructureUpdate();
        LogUtils.showInfo("refresh success");
    }
}
