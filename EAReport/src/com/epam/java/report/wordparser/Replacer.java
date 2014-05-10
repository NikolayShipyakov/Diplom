package com.epam.java.report.wordparser;

import com.epam.java.report.wordparser.beans.ObjectBean;
import com.epam.java.report.wordparser.beans.PackageBean;
import com.epam.java.report.wordparser.beans.ParameterBean;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

import java.io.*;
import java.util.List;

public class Replacer {
    XWPFDocument doc = null;


    public void createResultDoc(List<PackageBean> pckgBeans, String path) throws Exception {
        int pckgCounter = 0;

        InputStream fs = null;
        try {
            fs = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        doc = null;
        try {
            doc = new XWPFDocument(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }

        PackageBean currentPackageBean = null;
        ObjectBean currentObjectBean = null;
        for (int i = 0; i < doc.getParagraphs().size(); i++) {
            XWPFParagraph paragraph = doc.getParagraphs().get(i);
            String paragraphText = paragraph.getText();
            if (Utils.PACKAGE_OPEN_PATTERN.matcher(paragraphText).matches()) {
                for (int k = 0; k < pckgBeans.size(); k++) {
                    if (paragraphText.indexOf("{['" + pckgBeans.get(k).getName() + "']}") >= 0) {
                        currentPackageBean = pckgBeans.remove(k);
                    }
                }
                // Считаем парграф за строку пока что
                for (int j = 0; j < paragraph.getCTP().getRArray().length; j++) {
                    CTR run = paragraph.getCTP().getRArray()[j];
                    for (int l = 0; l < run.getTArray().length; l++) {
                        CTText text = run.getTArray()[l];
                        if (text.getStringValue().indexOf(currentPackageBean.getName()) > -1) {
                            CTText openPack = ((l - 1) > -1) ? run.getTArray()[l -1] : ((j - 1) > -1) ? paragraph.getCTP().getRArray()[j - 1].getTArray()[paragraph.getCTP().getRArray()[j - 1].getTArray().length - 1]  : null;
                            CTText closePack = ((l + 1) < run.getTArray().length) ? run.getTArray()[l + 1] : ((j + 1) < paragraph.getCTP().getRArray().length) ? paragraph.getCTP().getRArray()[j + 1].getTArray()[paragraph.getCTP().getRArray()[j - 1].getTArray().length - 1]  : null;

                            if (openPack != null && closePack!=null && openPack.getStringValue().indexOf("{[") > -1 && closePack.getStringValue().indexOf("]}") > -1) {
                                text.setStringValue("");
                                openPack.setStringValue("");
                                closePack.setStringValue("");
                            }
                        }
                    }
                }
            }
            if (Utils.OBJECT_OPEN_PATTERN.matcher(paragraphText).matches()) {
                for (int k = 0; k < currentPackageBean.getObjects().size(); k++) {
                    if (paragraphText.indexOf("<" + currentPackageBean.getObjects().get(k).getName()) >= 0) {
                        currentObjectBean = currentPackageBean.getObjects().remove(k);
                    }

                }
                // Считаем парграф за строку пока что
                for (int j = 0; j < paragraph.getCTP().getRArray().length; j++) {
                    CTR run = paragraph.getCTP().getRArray()[j];
                    for (int l = 0; l < run.getTArray().length; l++) {
                        CTText text = run.getTArray()[l];
                        if (text.getStringValue().indexOf(currentObjectBean.getName()) > -1) {
                            clear(i, j, l, ElementTypes.OBJECT_OPEN);
                        }
                    }
                }

            }

            if (Utils.PARAMETER_PATTERN.matcher(paragraphText).find()) {
                ParameterBean parameterBean = null;
                for (ParameterBean param : currentObjectBean.getParameters()) {
                    if(paragraphText.indexOf("{" + param.getName()) > -1) {
                        parameterBean = param;
                        break;
                    }
                }

                for (int j = 0; j < paragraph.getCTP().getRArray().length; j++) {
                    CTR run = paragraph.getCTP().getRArray()[j];
                    for (int l = 0; l < run.getTArray().length; l++) {
                        CTText text = run.getTArray()[l];
                        if (text.getStringValue().indexOf(parameterBean.getName()) > -1) {
                            clear(i, j, l, ElementTypes.PARAMETER_OPEN);
                            text.setStringValue(parameterBean.getParameterValue().get(0));
                        }
                    }
                }
            }

            if(Utils.OBJECT_CLOSE_PATTERN.matcher(paragraphText).matches()){
                    for (int j = 0; j < paragraph.getCTP().getRArray().length; j++) {
                        CTR run = paragraph.getCTP().getRArray()[j];
                        for (int l = 0; l < run.getTArray().length; l++) {
                            CTText text = run.getTArray()[l];
                            if (text.getStringValue().indexOf(currentObjectBean.getName()) > -1) {
                                clear(i, j, l, ElementTypes.OBJECT_CLOSE);
                            }
                        }
                }
            }
            if(Utils.PACKAGE_CLOSE_PATTERN.matcher(paragraphText).matches()){
                for (int j = 0; j < paragraph.getCTP().getRArray().length; j++) {
                    CTR run = paragraph.getCTP().getRArray()[j];
                    for (int l = 0; l < run.getTArray().length; l++) {
                        CTText text = run.getTArray()[l];
                        if (text.getStringValue().indexOf(currentPackageBean.getName()) > -1) {
                            clear(i, j, l, ElementTypes.PACKAGE_CLOSE);
                        }
                    }
                }
            }
        }

        try {
            doc.write(new FileOutputStream("C:\\tmpl.docx"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clear(int paragraphNumber, int ctrNumber, int cttNumber, ElementTypes type){
        String openSymbol = "";
        String closeSymbol = "";
        switch (type){
            case PACKAGE_OPEN:
                openSymbol = "{['";
                closeSymbol = "']}";
                break;
            case OBJECT_OPEN:
                openSymbol = "<";
                closeSymbol = ">";
                break;
            case PARAMETER_OPEN:
                openSymbol = "{";
                closeSymbol = "}";
                break;
            case PACKAGE_CLOSE:
                openSymbol = "{[";
                closeSymbol = "]}";
                break;
            case OBJECT_CLOSE:
                openSymbol = "<";
                closeSymbol = ">";
                break;
        }
        boolean active = true;
        List<XWPFParagraph> paragraphs = doc.getParagraphs();
        paragraphs.get(paragraphNumber).getCTP().getRArray(ctrNumber).getTArray(cttNumber).setStringValue("");
        for(int i = paragraphNumber; (active && i < paragraphs.size()); i++){
            for (int j =  ((ctrNumber + 1) < paragraphs.get(i).getCTP().getRArray().length) ? ctrNumber + 1 : 0; (active && j < paragraphs.get(i).getCTP().getRArray().length); j ++){
                for(int k = ((cttNumber + 1) < paragraphs.get(i).getCTP().getRArray(j).getTArray().length) ? cttNumber + 1 : 0; (active && k < paragraphs.get(i).getCTP().getRArray(j).getTArray().length); k ++){
                    if(active) {
                        String strValue = paragraphs.get(i).getCTP().getRArray(j).getTArray()[k].getStringValue();
                        paragraphs.get(i).getCTP().getRArray(j).getTArray()[k].setStringValue("");
                        if (strValue.indexOf(closeSymbol) >= 0) {
                            active = false;
                        }
                    }
                }
              }
        }
        active = true;
        for(int i = paragraphNumber; (active && i >= 0); i --){
            for (int j = ((ctrNumber - 1) >= 0) ? ctrNumber - 1 : paragraphs.get(i).getCTP().getRArray().length - 1; (active && j >= 0); j --){
                for(int k = ((cttNumber - 1) >= 0) ? cttNumber - 1 : paragraphs.get(i).getCTP().getRArray(j).getTArray().length - 1; (active && k >= 0); k --){
                    if (active) {
                        String strValue = paragraphs.get(i).getCTP().getRArray(j).getTArray()[k].getStringValue();
                        paragraphs.get(i).getCTP().getRArray(j).getTArray()[k].setStringValue("");
                        if (strValue.indexOf(openSymbol) >= 0) {
                            active = false;
                        }
                    }
                }
            }
        }
    }

    private enum ElementTypes{
        PACKAGE_OPEN, OBJECT_OPEN, PARAMETER_OPEN, PACKAGE_CLOSE, OBJECT_CLOSE, PARAMETER_CLOSE
    }
}
