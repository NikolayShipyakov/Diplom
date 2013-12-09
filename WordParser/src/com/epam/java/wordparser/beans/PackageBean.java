package com.epam.java.wordparser.beans;

public class PackageBean {
    private String name;
    private boolean root;

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
}
