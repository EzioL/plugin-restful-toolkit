package com.ezio.plugin.navigator.apiView;

import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/3/3
 */
public abstract class ApiViewFactoryEx extends ApiViewFactory {

    @Nullable
    public abstract ApiViewWrapper getApiViewWrapper();


    public static ApiViewFactoryEx getInstanceEx(final Project project) {
        return (ApiViewFactoryEx)getInstance(project);
    }

    public abstract void runWhenInitialized(@NotNull Runnable runnable);
}
