package com.epam.java.ea.com.epam.java.ea.enums;

public enum DiagramType {
    ACTIVITY,
    BPMN,
    CLASS,
    COMPONENT,
    REQUIREMENT,
    SEQUENCE,
    USECASE;

    private static final String ACTIVITY_NAME = "Activity";
    private static final String BPMN_NAME = "BPMN";
    private static final String CLASS_NAME = "Class";
    private static final String COMPONENT_NAME = "Component";
    private static final String REQUIREMENT_NAME = "Requirement";
    private static final String SEQUENCE_NAME = "Sequence";
    private static final String USECASE_NAME = "Use Case";

    public static DiagramType getDiagramType(String name) {
        if (ACTIVITY_NAME.equals(name)) return ACTIVITY;
        if (BPMN_NAME.equals(name)) return BPMN;
        if (CLASS_NAME.equals(name)) return CLASS;
        if (COMPONENT_NAME.equals(name)) return COMPONENT;
        if (REQUIREMENT_NAME.equals(name)) return REQUIREMENT;
        if (SEQUENCE_NAME.equals(name)) return SEQUENCE;
        if (USECASE_NAME.equals(name)) return USECASE;
        return null;
    }
}
