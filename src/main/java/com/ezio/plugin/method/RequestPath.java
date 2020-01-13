package com.ezio.plugin.method;

/**
 * Here be dragons !
 *
 * @author: Ezio
 * created on 2020/1/13
 */
public class RequestPath {
    String path;
    String method;

    public RequestPath(String path, String method) {
        this.path = path;
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

}
