package com.ezio.plugin.navigator.apiView;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ex.ToolWindowEx;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/3/3
 */
public class ApiViewWrapperImpl implements ApiViewWrapper, Disposable {

    private final Project myProject;
    private final ToolWindowEx myToolWindow;

    public ApiViewWrapperImpl(Project myProject, ToolWindowEx myToolWindow) {
        this.myProject = myProject;
        this.myToolWindow = myToolWindow;
    }

    @Override
    public void dispose() {

    }
}
