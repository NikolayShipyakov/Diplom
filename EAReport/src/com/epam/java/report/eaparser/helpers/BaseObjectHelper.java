package com.epam.java.report.eaparser.helpers;

import java.lang.reflect.Method;

public class BaseObjectHelper {

    private Object eaObject;

    public BaseObjectHelper(Object eaObject) {
        this.eaObject = eaObject;
    }

    public String getAttributeByName(String attrName) {
        try {
            Method method = eaObject.getClass().getMethod("Get" + Character.toUpperCase(attrName.charAt(0)) + attrName.substring(1));
            if (method != null) {
                return method.invoke(eaObject).toString();

            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
