package com.epam.java.ea.parser;

import com.epam.java.ea.beans.EAPackage;

import java.util.List;

public class Model {
    private List<EAPackage> packages;

    public List<EAPackage> getPackages() {
        return packages;
    }

    public void setPackages(List<EAPackage> packages) {
        this.packages = packages;
    }
}
