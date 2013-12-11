package com.epam.java.wordparser.beans;

import java.util.List;

public class PackageBean {
    private String name;
    private boolean root;
    private List<ObjectBean> objects;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    public List<ObjectBean> getObjects() {
        return objects;
    }

    public void setObjects(List<ObjectBean> objects) {
        this.objects = objects;
    }
}
