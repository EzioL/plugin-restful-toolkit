package com.ezio.plugin.helper;

import com.ezio.plugin.method.Parameter;
import com.intellij.openapi.module.Module;
import com.intellij.psi.*;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.Optional;

import static com.ezio.plugin.annotation.SpringRequestParamAnnotation.*;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/3/10
 */
public class PsiMethodHelper {

    PsiMethod psiMethod;
    Module myModule;

    public PsiMethodHelper(PsiMethod psiMethod, Module myModule) {
        this.psiMethod = psiMethod;
        this.myModule = myModule;
    }


    public List<Parameter> getParameterList() {
        List<Parameter> parameterList = Lists.newArrayList();
        PsiParameterList psiParameterList = psiMethod.getParameterList();
        PsiParameter[] psiParameters = psiParameterList.getParameters();
        for (PsiParameter psiParameter : psiParameters) {
            String paramType = psiParameter.getType().getCanonicalText();
            //忽略 request response
            if ("javax.servlet.http.HttpServletRequest".equals(paramType) || "javax.servlet.http.HttpServletResponse".equals(paramType)) {
                continue;
            }
            PsiModifierList modifierList = psiParameter.getModifierList();
            assert modifierList != null;
            boolean requestBodyFound = modifierList.findAnnotation(REQUEST_BODY.getQualifiedName()) != null;
            Optional<PsiAnnotation> pathVariable = Optional.ofNullable(modifierList.findAnnotation(PATH_VARIABLE.getQualifiedName()));
            pathVariable.ifPresent(e -> {
                String requestName = getAnnotationValue(e);
                Parameter parameter = new Parameter(paramType, requestName != null ? requestName : psiParameter.getName())
                        .setRequired(true).requestBodyFound(requestBodyFound);
                parameterList.add(parameter);
            });
            Optional<PsiAnnotation> requestParam = Optional.ofNullable(modifierList.findAnnotation(REQUEST_PARAM.getQualifiedName()));
            requestParam.ifPresent(e -> {
                String requestName = getAnnotationValue(e);
                Parameter parameter = new Parameter(paramType, requestName != null ? requestName : psiParameter.getName()).setRequired(true).requestBodyFound(requestBodyFound);
                parameterList.add(parameter);
            });

            if (!pathVariable.isPresent() && !requestParam.isPresent()) {
                Parameter parameter = new Parameter(paramType, psiParameter.getName()).requestBodyFound(requestBodyFound);
                parameterList.add(parameter);
            }
        }
        return parameterList;
    }

    public String getAnnotationValue(PsiAnnotation annotation) {
        String paramName = null;
        PsiAnnotationMemberValue attributeValue = annotation.findDeclaredAttributeValue("value");
        if (attributeValue instanceof PsiLiteralExpression) {
            paramName = (String) ((PsiLiteralExpression) attributeValue).getValue();
        }
        return paramName;
    }

}
