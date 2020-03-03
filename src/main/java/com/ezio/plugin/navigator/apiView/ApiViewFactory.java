package com.ezio.plugin.navigator.apiView;

import com.intellij.openapi.project.Project;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/3/3
 */
public abstract class ApiViewFactory {


    public static ApiViewFactory getInstance(Project project) {
        return project.getService(ApiViewFactory.class);
    }

}
