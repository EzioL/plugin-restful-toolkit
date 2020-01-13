package com.ezio.plugin.annotation;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/1/13
 */
public enum SpringRequestParamAnnotation implements PathMappingAnnotation {
    //
    REQUEST_PARAM("RequestParam", "org.springframework.web.bind.annotation.RequestParam"),
    REQUEST_BODY("RequestBody", "org.springframework.web.bind.annotation.RequestBody"),
    PATH_VARIABLE("PathVariable", "org.springframework.web.bind.annotation.PathVariable");

    SpringRequestParamAnnotation(String mainName, String qualifiedName) {
        this.mainName = mainName;
        this.qualifiedName = qualifiedName;
    }

    private String mainName;
    private String qualifiedName;

    @Override
    public String getMainName() {
        return null;
    }

    @Override
    public String getQualifiedName() {
        return null;
    }


}
