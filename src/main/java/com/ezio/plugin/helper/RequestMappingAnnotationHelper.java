package com.ezio.plugin.helper;

import com.ezio.plugin.annotation.SpringRequestMethodAnnotation;
import com.ezio.plugin.method.RequestPath;
import com.ezio.plugin.utils.Optionals;
import com.google.common.collect.Lists;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/1/13
 */
public class RequestMappingAnnotationHelper {

    public static List<RequestPath> getRequestPaths(PsiClass psiClass) {


        return Optional.ofNullable(psiClass.getModifierList())
                .map(modifierList -> {
                    List<SpringRequestMethodAnnotation> annotationList = Lists.newArrayList(SpringRequestMethodAnnotation.values());

                    return Lists.newArrayList(modifierList.getAnnotations()).stream()
                            .filter(annotation ->
                                    annotationList.stream()
                                            .anyMatch(e -> Objects.equals(annotation.getQualifiedName(), e.getQualifiedName())))
                            .findFirst();
                })
                .map(optional -> optional.map(e -> getRequestMappings(e, ""))
                        .orElseGet(() -> {
                            // 找它父类
                            PsiClass superClass = psiClass.getSuperClass();
                            if (Objects.nonNull(superClass) && !Objects.equals(superClass.getQualifiedName(), "java.lang.Object")) {
                                return getRequestPaths(superClass);
                            } else {
                                return Lists.newArrayList(new RequestPath("/", null));
                            }

                        }))
                .orElse(Lists.newArrayList());
    }

    private static List<RequestPath> getRequestMappings(PsiAnnotation annotation, String defaultValue) {


        return SpringRequestMethodAnnotation.getByQualifiedName(annotation.getQualifiedName())
                .map(requestMethodAnnotation -> {
                    // RequestMapping 如果没有指定具体method，不写的话，默认支持所有HTTP请求方法
                    return Optional.ofNullable(requestMethodAnnotation.getMainName())
                            .map(Lists::newArrayList)
                            .orElseGet(() -> (ArrayList<String>) PsiAnnotationHelper
                                    .getAnnotationAttributeValues(annotation, "method"));
                })
                .map(methodList -> {
                    // 没有设置 value，默认方法名
                    return Optionals.withSupplier(PsiAnnotationHelper.getAnnotationAttributeValues(annotation, "value"),
                            CollectionUtils::isNotEmpty, () -> PsiAnnotationHelper.getAnnotationAttributeValues(annotation, "path"))
                            .filter(CollectionUtils::isNotEmpty)
                            .orElse(Lists.newArrayList(defaultValue))
                            .stream()
                            .flatMap(path -> methodList.stream().map(method -> new RequestPath(path, method)))
                            .collect(Collectors.toList());
                })
                .orElse(Lists.newArrayList());
    }
}
