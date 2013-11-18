package com.epam.java.ea.parser;

import com.epam.java.ea.beans.EAPackage;
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
            EAPackage packImpl = new EAPackage();
            packImpl.setName(p.GetName());
            packImpl.setPackages(innerPackages);
            packages.add(packImpl);
        }
        return packages;
    }
}
