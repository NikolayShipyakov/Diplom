package com.epam.java.wordparser.beans;

import java.util.List;

public class ObjectBean {
    private String name;
    private List<ParameterBean> parameters;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ParameterBean> getParameters() {
        return parameters;
    }

    public void setParameters(List<ParameterBean> parameters) {
        this.parameters = parameters;
    }
}
