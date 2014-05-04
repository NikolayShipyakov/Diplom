package com.epam.java.report.wordparser;

import com.epam.java.report.wordparser.beans.ObjectBean;
import com.epam.java.report.wordparser.beans.PackageBean;
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
                    // Считаем парграф за строку пока что
                    for (int j = 0; j < paragraph.getCTP().getRArray().length; j++) {
                        CTR run = paragraph.getCTP().getRArray()[j];
                        for (int l = 0; l < run.getTArray().length; l++) {
                            CTText text = run.getTArray()[l];
                            if (text.getStringValue().indexOf(currentObjectBean.getName()) > -1) {
                                clear(i, k, j, ElementTypes.OBJECT);
                            }
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

    private void clear(int packageNumber, int ctrNumber, int cttNumber, ElementTypes type){
        String openSymbol = "";
        String closeSymbol = "";
        switch (type){
            case PACKAGE:
                openSymbol = "{['";
                closeSymbol = "']}";
                break;
            case OBJECT:
                openSymbol = "<";
                closeSymbol = ">";
                break;
            case PARAMETER:
                openSymbol = "{";
                closeSymbol = "}";
                break;
        }
        boolean active = true;
        List<XWPFParagraph> paragraphs = doc.getParagraphs();
        for(int i = packageNumber; i < paragraphs.size(); i++){
            for (int j = (i == packageNumber) ? (((ctrNumber + 1) < paragraphs.get(i).getCTP().getRArray().length) ? ctrNumber + 1 : 0) : 0 ; j < paragraphs.get(i).getCTP().getRArray().length; j ++){
                for(int k = (j == ctrNumber) ? (((cttNumber + 1) < paragraphs.get(i).getCTP().getRArray(j).getTArray().length) ? cttNumber + 1 : 0) : 0 ; k < paragraphs.get(i).getCTP().getRArray(j).getTArray().length; k ++){
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
        for(int i = packageNumber; i >= 0; i --){
            for (int j = (i == packageNumber) ? (((ctrNumber - 1) >= 0) ? ctrNumber - 1 : paragraphs.get(i).getCTP().getRArray().length - 1) : paragraphs.get(i).getCTP().getRArray().length - 1; j >= 0; j --){
                for(int k = (j == ctrNumber) ? (((cttNumber - 1) >= 0) ? cttNumber - 1 : paragraphs.get(i).getCTP().getRArray(j).getTArray().length - 1) : paragraphs.get(i).getCTP().getRArray(j).getTArray().length - 1; k >= 0; k --){
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
        PACKAGE, OBJECT, PARAMETER
    }
}
