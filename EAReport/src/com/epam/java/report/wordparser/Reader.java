package com.epam.java.report.wordparser;

import com.epam.java.report.wordparser.beans.PackageBean;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Reader {

    public static void main(String[] args) throws Exception {
        /**This is the document that you want to read using Java.**/
        //String fileName = "D:\\Projects\\NPA_DIPLOM\\WordParser\\word_docs\\test2.docx";
        String fileName = "D:\\programming\\java\\diplom\\Diplom\\WordParser\\word_docs\\test2.docx";
        open(fileName);
    }

    public static List<PackageBean> open(String fileName) {

        InputStream fs = null;
        try {
            fs = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        XWPFDocument doc = null;
        try {
            doc = new XWPFDocument(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder textDoc = new StringBuilder();
        List<String> paragraphs = new LinkedList<String>();
        for (Integer i = 0; i < doc.getBodyElements().size(); i++) {
            IBodyElement bodyElement = doc.getBodyElements().get(i);
            if (bodyElement instanceof XWPFParagraph) {
                XWPFParagraph paragraph = (XWPFParagraph) bodyElement;
                paragraphs.add(paragraph.getParagraphText());
            }
            if(bodyElement instanceof XWPFTable){
                XWPFTable table = (XWPFTable)bodyElement;
                for(XWPFTableRow row:table.getRows()){
                    StringBuilder rowText = new StringBuilder();
                    for(XWPFTableCell cell:row.getTableCells()){
                        rowText.append(cell.getText()).append(" ");
                    }
                    paragraphs.add(rowText.toString());
                }
            }
        }
        try {
            doc.write(new FileOutputStream("C:\\b.docx"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parser parser = new Parser(paragraphs);
        return parser.parseText();
    }
}
