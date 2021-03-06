package com.ezio.plugin.annotation;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/1/13
 */
public enum SpringWebAnnotation implements PathMappingAnnotation {
    //
    CONTROLLER("Controller", "org.springframework.stereotype.Controller"),
    REST_CONTROLLER("RestController", "org.springframework.web.bind.annotation.RestController"),
    FEIGN_CLIENT("FeignClient", "org.springframework.cloud.openfeign.FeignClient"),
    ;

    private String mainName;
    private String qualifiedName;

    SpringWebAnnotation(String mainName, String qualifiedName) {
        this.mainName = mainName;
        this.qualifiedName = qualifiedName;
    }

    @Override
    public String getMainName() {
        return mainName;
    }

    @Override
    public String getQualifiedName() {
        return qualifiedName;
    }


}
