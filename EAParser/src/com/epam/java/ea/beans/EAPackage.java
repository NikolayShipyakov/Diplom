package com.epam.java.ea.beans;

import java.util.ArrayList;
import java.util.List;

public class EAPackage {

    private String name;
    private List<EAPackage> packages = new ArrayList<EAPackage>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EAPackage> getPackages(){
        return packages;
    }

    public void setPackages(List<EAPackage> packages){
        this.packages = packages;
    }

    public void addPackage(EAPackage p){
        packages.add(p);
    }
}
