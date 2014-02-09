package com.epam.java.report.wordparser.beans;

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

    public String toString() {
        String result = "name = " + name;
        if (parameters != null) {
            result += ", parameters [";
            for (ParameterBean parameter : parameters) {
                result += parameter.toString() + ",";
            }
            result += "]";
        }
        return result;
    }
}
