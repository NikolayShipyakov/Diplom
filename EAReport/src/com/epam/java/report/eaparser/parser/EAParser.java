package com.epam.java.report.eaparser.parser;

import com.epam.java.report.eaparser.beans.EADiagram;
import com.epam.java.report.eaparser.beans.EAElement;
import com.epam.java.report.eaparser.beans.EAPackage;
import com.epam.java.report.eaparser.com.epam.java.ea.enums.DiagramType;
import com.epam.java.report.eaparser.com.epam.java.ea.enums.ElementType;
import com.epam.java.report.eaparser.helpers.BaseObjectHelper;
import com.epam.java.report.wordparser.beans.ObjectBean;
import com.epam.java.report.wordparser.beans.PackageBean;
import com.epam.java.report.wordparser.beans.ParameterBean;
import org.sparx.*;
import org.sparx.Package;

import java.util.*;

public class EAParser {
    private static final Repository r = new Repository();

    public EAParser(String path) {
        boolean b = r.OpenFile(path);
    }

    public Model search(List<PackageBean> searchParameters) {
        Model model = new Model();
        model.setPackages(getModels(searchParameters));
        return null;
    }

    private List<EAPackage> getModels(List<PackageBean> searchBeans) {
        Set<String> packageNames = new HashSet<String>();
        for (PackageBean pb : searchBeans) {
            List<EAPackage> packages = new ArrayList<EAPackage>();
            boolean isRootPackage = pb.isRoot();
            List<org.sparx.Package> pkgs = new ArrayList<Package>();
            if (isRootPackage) {
                for (org.sparx.Package p : r.GetModels()) {
                    for (org.sparx.Package pack : p.GetPackages()) {
                        if (pack.GetName().equals(pb.getName()) || pack.GetAlias().equals(pb.getName())) {
                            pkgs.add(pack);
                        }
                    }
                }
            }else{
                  pkgs = getPackage(pb.getName());
            }
            searchInPackage(pkgs, pb);
        }
        return null;
    }

    private void searchInPackage(List<org.sparx.Package> pkgs, PackageBean searchBean){
                List<ObjectBean> objects = searchBean.getObjects();
                for (ObjectBean o : objects) {
                    String objectName = o.getName();
                    if (objectName.equals("diagram")) {
                        for (org.sparx.Package pack : pkgs) {
                            org.sparx.Collection<Diagram> diagrams = pack.GetDiagrams();
                            for (Diagram d : diagrams) {
                                for (ParameterBean param : o.getParameters()){
                                   System.out.println("Param." + param.getName() + "=" + new BaseObjectHelper(d).getAttributeByName(param.getName()));
                                }
                            }
                        }
                    }
                    if(objectName.equals("element")){
                        for (org.sparx.Package pack : pkgs) {
                            org.sparx.Collection<Element> elements = pack.GetElements();
                            for (Element e : elements) {
                                for (ParameterBean param : o.getParameters()){
                                    System.out.println("Param." + param.getName() + "=" + new BaseObjectHelper(e).getAttributeByName(param.getName()));
                                }
                            }
                        }
                    }
                    if(objectName.equals("package")){

                    }
                }
    }

    private List<org.sparx.Package> getPackage(String packageName){
        ArrayList<org.sparx.Package> result = new ArrayList<Package>();
        for (org.sparx.Package p : r.GetModels()) {
            if (p.GetName().equals(packageName)) {
                result.add(p);
            }
            result.addAll(getInnerPackages(p, packageName));
        }
        return result;
    }

    private List<org.sparx.Package> getInnerPackages(org.sparx.Package innerPackage,String packageName){
        List<org.sparx.Package> result = new ArrayList<Package>();
        if (innerPackage.GetPackages().GetCount() != 0){
            result = new ArrayList<Package>();
            for(org.sparx.Package pack : innerPackage.GetPackages()){
                if (pack.GetName().equals(packageName)) {
                  result.add(pack);
                }
                if (pack.GetPackages().GetCount() > 0) {
                    result.addAll(getInnerPackages(pack, packageName));
                }
            }
        }
        return result;
    }

    private void searchDiagramm(org.sparx.Package pack){

    }

    private void searchElement(org.sparx.Package pack){

    }

    private void searchPackage(org.sparx.Package pack){

    }

    private List<EAPackage> getPackages(org.sparx.Package pack) {
        List<EAPackage> packages = new ArrayList<EAPackage>();
        for (org.sparx.Package p : pack.GetPackages()) {
            List<EAPackage> innerPackages = getPackages(p);
            List<EADiagram> diagrams = getDiagrams(p);
            List<EAElement> elements = getElements(p);
            EAPackage packImpl = new EAPackage();
            packImpl.setName(p.GetName());
            packImpl.setPackages(innerPackages);
            packImpl.setDiagrams(diagrams);
            packImpl.setElements(elements);
            packages.add(packImpl);
        }
        return packages;
    }

    private List<EADiagram> getDiagrams(org.sparx.Package pack) {
        List<EADiagram> diagrams = new ArrayList<EADiagram>();
        for (Diagram d : pack.GetDiagrams()) {
            EADiagram diagram = new EADiagram();
            diagram.setName(d.GetName());
            diagram.setType(DiagramType.getDiagramType(d.GetType()));
            System.out.println(diagram.getType());
            diagrams.add(diagram);
        }
        return diagrams;
    }

    private List<EAElement> getElements(org.sparx.Package pack) {
        List<EAElement> elements = new ArrayList<EAElement>();
        for (Element e : pack.GetElements()) {
            EAElement element = new EAElement();
            element.setName(e.GetName());
            element.setType(ElementType.getElementByName(e.GetType()));
            elements.add(element);
        }
        return elements;
    }
}
