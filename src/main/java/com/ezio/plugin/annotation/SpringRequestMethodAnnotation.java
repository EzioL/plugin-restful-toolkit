package com.ezio.plugin.annotation;

import com.ezio.plugin.utils.Optionals;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/1/13
 */
public enum SpringRequestMethodAnnotation implements PathMappingAnnotation {
    //
    REQUEST_MAPPING(null, "org.springframework.web.bind.annotation.RequestMapping"),
    GET_MAPPING("GET", "org.springframework.web.bind.annotation.GetMapping"),
    POST_MAPPING("POST", "org.springframework.web.bind.annotation.PostMapping"),
    PUT_MAPPING("PUT", "org.springframework.web.bind.annotation.PutMapping"),
    DELETE_MAPPING("DELETE", "org.springframework.web.bind.annotation.DeleteMapping"),
    PATCH_MAPPING("PATCH", "org.springframework.web.bind.annotation.PatchMapping");

    SpringRequestMethodAnnotation(String mainName, String qualifiedName) {
        this.mainName = mainName;
        this.qualifiedName = qualifiedName;

    }

    private String mainName;
    private String qualifiedName;

    @Override
    public String getMainName() {
        return mainName;
    }

    @Override
    public String getQualifiedName() {
        return qualifiedName;
    }


    public static Optional<SpringRequestMethodAnnotation> getByQualifiedName(String qualifiedName) {


        return Optionals.ofPredicable(qualifiedName, StringUtils::isNotBlank)
                .map(e -> Stream.of(SpringRequestMethodAnnotation.values())
                        .filter(s -> s.getQualifiedName().equals(qualifiedName)).findFirst())
                .filter(Optional::isPresent)
                .map(Optional::get);

    }

    public static SpringRequestMethodAnnotation getByMainName(String requestMapping) {
        for (SpringRequestMethodAnnotation springRequestAnnotation : SpringRequestMethodAnnotation.values()) {
            if (springRequestAnnotation.getQualifiedName().endsWith(requestMapping)) {
                return springRequestAnnotation;
            }
        }
        return null;

    }
}
