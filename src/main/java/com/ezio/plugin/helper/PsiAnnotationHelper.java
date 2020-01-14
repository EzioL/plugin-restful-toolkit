package com.ezio.plugin.helper;

import com.intellij.psi.*;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/1/13
 */
public class PsiAnnotationHelper {

    public static List<String> getAnnotationAttributeValues(PsiAnnotation annotation, String attr) {

        PsiAnnotationMemberValue value = annotation.findDeclaredAttributeValue(attr);

        List<String> values = Lists.newArrayList();
        //只有注解
        //一个值 class com.intellij.psi.impl.source.tree.java.PsiLiteralExpressionImpl
        //多个值  class com.intellij.psi.impl.source.tree.java.PsiArrayInitializerMemberValueImpl
        if (value instanceof PsiReferenceExpression) {
            values.add(value.getText());
        } else if (value instanceof PsiLiteralExpression) {
            Optional.ofNullable(((PsiLiteralExpression) value).getValue())
                    .ifPresent(e -> values.add(e.toString()));
        } else if (value instanceof PsiArrayInitializerMemberValue) {
            Stream.of(((PsiArrayInitializerMemberValue) value).getInitializers())
                    .forEach(e -> values.add(e.getText().replaceAll("\"", "")));
        }

        return values;
    }

}
