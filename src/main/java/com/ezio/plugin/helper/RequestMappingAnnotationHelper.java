package com.ezio.plugin.helper;

import com.ezio.plugin.annotation.SpringRequestMethodAnnotation;
import com.ezio.plugin.method.RequestPath;
import com.ezio.plugin.utils.Optionals;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/1/13
 */
public class RequestMappingAnnotationHelper {

    private static final ImmutableList<SpringRequestMethodAnnotation> SPRING_REQUEST_METHOD_ANNOTATIONS =
            ImmutableList.copyOf(Lists.newArrayList(SpringRequestMethodAnnotation.values()));


    public static List<RequestPath> getRequestPaths(PsiClass psiClass) {

        Optional<PsiAnnotation> optional = Optional.ofNullable(psiClass.getModifierList())
                .flatMap(modifierList -> Lists.newArrayList(modifierList.getAnnotations()).stream()
                        .filter(annotation ->
                                SPRING_REQUEST_METHOD_ANNOTATIONS.stream()
                                        .anyMatch(e -> Objects.equals(annotation.getQualifiedName(), e.getQualifiedName())))
                        .findFirst());

        if (optional.isPresent()) {
            List<RequestPath> requestMappings = getRequestMappings(optional.get(), "");
            return requestMappings;
        }
        PsiClass superClass = psiClass.getSuperClass();
        if (Objects.nonNull(superClass) && !Objects.equals(superClass.getQualifiedName(), "java.lang.Object")) {
            return getRequestPaths(superClass);
        } else {
            return Lists.newArrayList(new RequestPath("/", null));
        }
    }


    public static List<RequestPath> getRequestPaths(PsiMethod psiMethod) {

        System.out.println("getRequestMappings: " + psiMethod.toString());

        return Stream.of(psiMethod.getModifierList().getAnnotations())
                .filter(annotation -> SPRING_REQUEST_METHOD_ANNOTATIONS.stream()
                        .anyMatch(e -> Objects.equals(annotation.getQualifiedName(), e.getQualifiedName()))
                )
                .flatMap(annotation -> getRequestMappings(annotation, "/").stream())
                .collect(Collectors.toList());
    }


    private static List<RequestPath> getRequestMappings(PsiAnnotation annotation, String defaultValue) {
        System.out.println("getRequestMappings: " + annotation.getQualifiedName());
        Optional<SpringRequestMethodAnnotation> optional = SpringRequestMethodAnnotation.getByQualifiedName(annotation.getQualifiedName());
        if (!optional.isPresent()) {
            return Lists.newArrayList();
        }

        List<String> methodList = Optional.ofNullable(optional.get().getMainName()).map(Lists::newArrayList)
                .orElseGet(() -> (ArrayList<String>) PsiAnnotationHelper.getAnnotationAttributeValues(annotation, "method"));

        List<String> valueList = Optionals.ofPredicable(PsiAnnotationHelper.getAnnotationAttributeValues(annotation, "value"), CollectionUtils::isNotEmpty)
                .orElseGet(() -> Optionals.ofPredicable(PsiAnnotationHelper.getAnnotationAttributeValues(annotation, "path"), CollectionUtils::isNotEmpty)
                        .orElse(Lists.newArrayList()));

        System.out.println("getRequestMappings: " + valueList);

        // 如果是类上的注解 不存在方法
        return Optionals.ofPredicable(methodList, CollectionUtils::isNotEmpty)
                .map(e -> {
                    return valueList.stream()
                            .flatMap(path -> methodList.stream().map(method -> new RequestPath(path, method)))
                            .collect(Collectors.toList());
                })
                .orElseGet(() -> {
                    return valueList.stream()
                            .map(path -> new RequestPath(path, null))
                            .collect(Collectors.toList());
                });
    }
}
