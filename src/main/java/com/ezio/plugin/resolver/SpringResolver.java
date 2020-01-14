package com.ezio.plugin.resolver;

import com.ezio.plugin.annotation.SpringWebAnnotation;
import com.ezio.plugin.helper.RequestMappingAnnotationHelper;
import com.ezio.plugin.method.RequestPath;
import com.ezio.plugin.navigator.domain.RestServiceItem;
import com.ezio.plugin.utils.Optionals;
import com.google.common.collect.Lists;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifierList;
import com.intellij.psi.impl.java.stubs.index.JavaAnnotationIndex;
import com.intellij.psi.search.GlobalSearchScope;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/1/13
 */
public class SpringResolver extends BaseServiceResolver {


    public static final Logger LOG = Logger.getInstance(SpringResolver.class);


    public SpringResolver(Module module) {
        this.module = module;
    }

    public SpringResolver(Project project) {
        this.project = project;
    }


    @Override
    public List<RestServiceItem> getRestServiceItemList(Project project, GlobalSearchScope globalSearchScope) {

        List<RestServiceItem> restServiceItemList = Stream.of(SpringWebAnnotation.values())
                .flatMap(controllerAnnotation ->
                        JavaAnnotationIndex.getInstance().get(controllerAnnotation.getMainName(), project, globalSearchScope).stream())
                .flatMap(psiAnnotation -> {
                    PsiModifierList psiModifierList = (PsiModifierList) psiAnnotation.getParent();
                    PsiElement psiElement = psiModifierList.getParent();
                    return getServiceItemList((PsiClass) psiElement).stream();
                })
                .collect(Collectors.toList());
        LOG.info(restServiceItemList.toString());
        return restServiceItemList;
    }

    protected List<RestServiceItem> getServiceItemList(PsiClass psiClass) {
        System.out.println("SpringClass: " + psiClass.getName());

        Optional<List<PsiMethod>> optional = Optionals.ofPredicable(Lists.newArrayList(psiClass.getMethods()), CollectionUtils::isNotEmpty);
        if (!optional.isPresent()) {
            return Lists.newArrayList();
        }
        List<RequestPath> classRequestPaths = RequestMappingAnnotationHelper.getRequestPaths(psiClass);

        System.out.println("ClassRequestPath: " + classRequestPaths);
        List<RestServiceItem> serviceItemList = optional.get().stream()
                .flatMap(psiMethod -> {
                    return RequestMappingAnnotationHelper.getRequestPaths(psiMethod).stream()
                            .flatMap(methodRequestPath ->
                                    classRequestPaths.stream().map(classRequestPath ->
                                            createRestServiceItem(psiMethod, classRequestPath.getPath(), methodRequestPath)));
                })
                .collect(Collectors.toList());
        System.out.println("RestServiceSize: " + serviceItemList.size());
        return serviceItemList;

    }
}
