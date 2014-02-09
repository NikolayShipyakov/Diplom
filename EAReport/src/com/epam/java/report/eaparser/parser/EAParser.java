package com.epam.java.report.eaparser.parser;

import com.epam.java.report.eaparser.beans.EADiagram;
import com.epam.java.report.eaparser.beans.EAElement;
import com.epam.java.report.eaparser.beans.EAPackage;
import com.epam.java.report.eaparser.com.epam.java.ea.enums.DiagramType;
import com.epam.java.report.eaparser.com.epam.java.ea.enums.ElementType;
import org.sparx.Diagram;
import org.sparx.Element;
import org.sparx.Repository;

import java.util.ArrayList;
import java.util.List;

public class EAParser {
    public Model parse(String path) {
        Repository r = new Repository();
        boolean b = r.OpenFile(path);
        Model model = new Model();
        model.setPackages(getModels(r));
        return null;
    }

    private List<EAPackage> getModels(Repository r) {
        List<EAPackage> packages = new ArrayList<EAPackage>();
        for (org.sparx.Package p : r.GetModels()) {
            List<EAPackage> innerPackages = getPackages(p);
            EAPackage pack = new EAPackage();
            pack.setName(p.GetName());
            pack.setPackages(innerPackages);
            packages.add(pack);
        }
        return packages;
    }

    private List<EAPackage> getPackages(org.sparx.Package pack) {
        List<EAPackage> packages = new ArrayList<EAPackage>();
        for(org.sparx.Package p: pack.GetPackages()){
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
