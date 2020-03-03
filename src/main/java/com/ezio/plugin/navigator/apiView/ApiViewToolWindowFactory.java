package com.ezio.plugin.navigator.apiView;

import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.ex.ToolWindowEx;
import org.jetbrains.annotations.NotNull;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/3/2
 */
public class ApiViewToolWindowFactory implements ToolWindowFactory, DumbAware {
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {

        ApiViewFactoryImpl factory = (ApiViewFactoryImpl) ApiViewFactory.getInstance(project);

        factory.initToolWindow((ToolWindowEx)toolWindow);
    }
}
