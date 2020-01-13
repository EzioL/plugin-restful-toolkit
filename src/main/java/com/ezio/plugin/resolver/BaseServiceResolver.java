package com.ezio.plugin.resolver;

import com.ezio.plugin.navigator.domain.RestServiceItem;
import com.ezio.plugin.utils.Optionals;
import com.intellij.openapi.project.Project;
import com.intellij.psi.search.GlobalSearchScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/1/13
 */
public abstract class BaseServiceResolver implements ServiceResolver {

    com.intellij.openapi.module.Module module;
    com.intellij.openapi.project.Project project;


    public abstract List<RestServiceItem> getRestServiceItemList(Project project, GlobalSearchScope globalSearchScope);


    @Override
    public List<RestServiceItem> findAllSupportedServiceItemsInModule() {
        return Optionals.ofPredicable(module, Objects::nonNull)
                .map(e -> {
                    GlobalSearchScope globalSearchScope = GlobalSearchScope.moduleScope(e);
                    return getRestServiceItemList(e.getProject(), globalSearchScope);
                })
                .orElse(new ArrayList<>());
    }

    @Override
    public List<RestServiceItem> findAllSupportedServiceItemsInProject() {
        return Optionals.ofPredicable(project, Objects::nonNull)
                .map(e -> {
                    GlobalSearchScope globalSearchScope = GlobalSearchScope.projectScope(e);
                    return getRestServiceItemList(e, globalSearchScope);
                })
                .orElse(new ArrayList<>());
    }

}
