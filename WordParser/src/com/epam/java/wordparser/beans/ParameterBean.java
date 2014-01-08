package com.epam.java.wordparser.beans;

public class ParameterBean {
    private String name;
    private boolean additionalCommand = false;
    private String parameter1;
    private String parameter2;
    private String parameter3;
    private String parameter4;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParameter4() {
        return parameter4;
    }

    public void setParameter4(String parameter4) {
        this.parameter4 = parameter4;
    }

    public String getParameter1() {
        return parameter1;
    }

    public void setParameter1(String parameter1) {
        this.parameter1 = parameter1;
    }

    public String getParameter2() {
        return parameter2;
    }

    public void setParameter2(String parameter2) {
        this.parameter2 = parameter2;
    }

    public String getParameter3() {
        return parameter3;
    }

    public void setParameter3(String parameter3) {
        this.parameter3 = parameter3;
    }

    public boolean isAdditionalCommand() {
        return additionalCommand;
    }

    public void setAdditionalCommand(boolean additionalCommand) {
        this.additionalCommand = additionalCommand;
    }
}
