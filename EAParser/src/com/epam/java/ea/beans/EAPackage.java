package com.epam.java.ea.beans;

import java.util.ArrayList;
import java.util.List;

public class EAPackage {

    private String name;
    private List<EAPackage> packages = null;
    private List<EADiagram> diagrams = null;
    private List<EAElement> elements = null;

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

    public List<EADiagram> getDiagrams() {
        return diagrams;
    }

    public void setDiagrams(List<EADiagram> diagrams) {
        this.diagrams = diagrams;
    }

    public List<EAElement> getElements() {
        return elements;
    }

    public void setElements(List<EAElement> elements) {
        this.elements = elements;
    }
}
