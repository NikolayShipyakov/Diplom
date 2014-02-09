package com.epam.java.report.eaparser.parser;

import com.epam.java.report.eaparser.beans.EAPackage;

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
