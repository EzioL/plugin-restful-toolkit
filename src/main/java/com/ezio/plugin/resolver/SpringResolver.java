package com.ezio.plugin.resolver;

import com.ezio.plugin.navigator.domain.RestServiceItem;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;

import java.util.List;


/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/1/13
 */
public class SpringResolver extends BaseServiceResolver {


    public SpringResolver(com.intellij.openapi.module.Module module) {
        this.module = module;
    }

    public SpringResolver(com.intellij.openapi.project.Project project) {
        this.project = project;
    }


    @Override
    public List<RestServiceItem> getRestServiceItemList(com.intellij.openapi.project.Project project, GlobalSearchScope globalSearchScope) {

        ProjectFileIndex.SERVICE.getInstance(project)
                .iterateContent(new com.intellij.openapi.roots.ContentIterator() {
                    @Override
                    public boolean processFile(@NotNull com.intellij.openapi.vfs.VirtualFile virtualFile) {
                        return false;
                    }
                });

//        Stream.of(SpringControllerAnnotation.values()).map(controllerAnnotation -> {
//
//
//        });
        return null;
    }
}
