package com.epam.java.eaparser.beans;

import com.epam.java.eaparser.com.epam.java.ea.enums.ElementType;

public class EAElement {
    private String name;
    private ElementType type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ElementType getType() {
        return type;
    }

    public void setType(ElementType type) {
        this.type = type;
    }
}
