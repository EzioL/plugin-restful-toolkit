package com.ezio.plugin.navigator.apiView;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ex.ToolWindowEx;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/3/3
 */
public class ApiViewFactoryImpl extends ApiViewFactoryEx {


    private final Project myProject;
    private ApiViewWrapperImpl myApiViewWrapper;
    private Runnable myRunWhenInitialized = null;

    public ApiViewFactoryImpl(Project myProject) {
        this.myProject = myProject;
    }

    public void initToolWindow(@NotNull ToolWindowEx toolWindow) {
        myApiViewWrapper = new ApiViewWrapperImpl(myProject, toolWindow);
        if (myRunWhenInitialized != null) {
            myRunWhenInitialized.run();
            myRunWhenInitialized = null;
        }
    }


    @Nullable
    @Override
    public ApiViewWrapper getApiViewWrapper() {
        return myApiViewWrapper;
    }

    @Override
    public void runWhenInitialized(@NotNull Runnable runnable) {

        if (myRunWhenInitialized != null) {
            runnable.run();
        } else {
            myRunWhenInitialized = runnable;
        }
    }
}
