package com.ezio.plugin.navigator.domain;

import com.intellij.openapi.module.Module;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/1/13
 */
public class RestServiceProject {

    public static final String DEFAULT_PORT = "8080";

    private String port = DEFAULT_PORT;

    private String appName;

    private com.intellij.openapi.module.Module module;

    private String moduleName;

    private java.util.List<RestServiceItem> restServiceItemList;


    public RestServiceProject(String port, com.intellij.openapi.module.Module module, java.util.List<RestServiceItem> restServices) {
        this.port = port;
        this.moduleName = module.getName();
        appName = moduleName;
        this.restServiceItemList = restServices;
    }

    @Override
    public String toString() {
        return appName + ":" + port;
    }


    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public com.intellij.openapi.module.Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

}
