package com.ezio.plugin.resolver;

import com.ezio.plugin.method.RequestPath;
import com.ezio.plugin.navigator.domain.RestServiceItem;
import com.ezio.plugin.utils.Optionals;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/1/13
 */
public abstract class BaseServiceResolver implements ServiceResolver {

    Module module;
    Project project;


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


    @NotNull
    protected RestServiceItem createRestServiceItem(PsiElement psiMethod, String classUriPath, RequestPath requestMapping) {


        if (!classUriPath.startsWith("/")) {
            classUriPath = "/".concat(classUriPath);
        }
        if (!classUriPath.endsWith("/")) {
            classUriPath = classUriPath.concat("/");
        }
        String methodPath = requestMapping.getPath();
        if (methodPath.startsWith("/")) {
            methodPath = methodPath.substring(1, methodPath.length());
        }
        String requestPath = classUriPath + methodPath;
        RestServiceItem item = new RestServiceItem(psiMethod, requestMapping.getMethod(), requestPath);
        Optional.ofNullable(module).ifPresent(item::setModule);
        return item;
    }

}
