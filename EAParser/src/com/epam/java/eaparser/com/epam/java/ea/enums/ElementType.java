package com.epam.java.eaparser.com.epam.java.ea.enums;

public enum ElementType {
    ACTION,
    ACTIVITY,
    ACTOR,
    CLASS,
    COMPONENT,
    ENTITY,
    EVENT,
    FEATURE,
    OBJECT,
    REQUIREMENT,
    STATE,
    USECASE;

    private static final String ACTION_NAME = "Action";
    private static final String ACTIVITY_NAME = "Activity";
    private static final String ACTOR_NAME = "Actor";
    private static final String CLASS_NAME = "Class";
    private static final String COMPONENT_NAME = "Component";
    private static final String ENTITY_NAME = "Entity";
    private static final String EVENT_NAME = "Event";
    private static final String FEATURE_NAME = "Feature";
    private static final String OBJECT_NAME = "Object";
    private static final String REQUIREMENT_NAME = "Requirement";
    private static final String STATE_NAME = "State";
    private static final String USECASE_NAME = "UseCase";

    public static ElementType getElementByName(String name) {
        if (ACTION_NAME.equals(name)) return ACTION;
        if (ACTIVITY_NAME.equals(name)) return ACTIVITY;
        if (ACTOR_NAME.equals(name)) return ACTOR;
        if (CLASS_NAME.equals(name)) return CLASS;
        if (COMPONENT_NAME.equals(name)) return COMPONENT;
        if (ENTITY_NAME.equals(name)) return ENTITY;
        if (EVENT_NAME.equals(name)) return EVENT;
        if (FEATURE_NAME.equals(name)) return FEATURE;
        if (OBJECT_NAME.equals(name)) return OBJECT;
        if (REQUIREMENT_NAME.equals(name)) return REQUIREMENT;
        if (STATE_NAME.equals(name)) return STATE;
        if (USECASE_NAME.equals(name)) return USECASE;
        return null;
    }
}
