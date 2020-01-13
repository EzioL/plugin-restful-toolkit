package com.ezio.plugin.navigator.component;

import com.intellij.openapi.components.ServiceManager;
import org.jetbrains.annotations.NotNull;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/1/13
 */
public class RestServiceNavigator implements com.intellij.openapi.components.ProjectComponent {

    public static final com.intellij.openapi.diagnostic.Logger LOG = com.intellij.openapi.diagnostic.Logger.getInstance(RestServiceNavigator.class);

    public static final String TOOL_WINDOW_ID = "RestService";


    public RestServiceNavigator(com.intellij.openapi.project.Project project) {
    }

    public static RestServiceNavigator getInstance(@NotNull com.intellij.openapi.project.Project project) {
        return ServiceManager.getService(project, RestServiceNavigator.class);
    }


    @Override
    public void initComponent() {

    }
}
