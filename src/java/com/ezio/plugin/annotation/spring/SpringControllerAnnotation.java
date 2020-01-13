package java.com.ezio.plugin.annotation.spring;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/1/13
 */
public enum SpringControllerAnnotation implements PathMappingAnnotation {
    //
    CONTROLLER("Controller", "org.springframework.stereotype.Controller"),
    REST_CONTROLLER("RestController", "org.springframework.web.bind.annotation.RestController");

    private String mainName;
    private String qualifiedName;

    SpringControllerAnnotation(String mainName, String qualifiedName) {
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
