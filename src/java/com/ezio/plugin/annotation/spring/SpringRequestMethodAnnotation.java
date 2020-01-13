package java.com.ezio.plugin.annotation.spring;

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


}
