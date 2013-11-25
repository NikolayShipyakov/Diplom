package com.epam.java.ea.beans;

import com.epam.java.ea.com.epam.java.ea.enums.DiagramType;

public class EADiagram {
    private String name;
    private DiagramType type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DiagramType getType() {
        return type;
    }

    public void setType(DiagramType type) {
        this.type = type;
    }
}
