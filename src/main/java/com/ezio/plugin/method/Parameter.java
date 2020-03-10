package com.ezio.plugin.method;

public class Parameter {

    private String paramType;
    private String paramName;
    private String defaultValue = null;
    private boolean required = false;
    private boolean requestBodyFound = false;

    public Parameter() {
    }

    public Parameter(String paramType, String paramName) {
        this.paramType = paramType;
        this.paramName = paramName;
    }

    public Parameter(String paramType, String paramName, String defaultValue) {
        this.paramType = paramType;
        this.paramName = paramName;
        this.defaultValue = defaultValue;
    }

    public Parameter requestBodyFound(boolean requestBodyFound) {
        this.requestBodyFound = requestBodyFound;
        return this;
    }

    public Parameter setRequired(boolean required) {
        this.required = required;
        return this;
    }
}
