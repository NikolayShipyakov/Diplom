package com.epam.java.wordparser;

import java.io.*;
import java.util.*;

import com.epam.java.wordparser.Parser;
import com.epam.java.wordparser.beans.PackageBean;
import org.apache.poi.POITextExtractor;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.*;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.extractor.ExtractorFactory;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hwpf.*;
import org.apache.poi.hwpf.extractor.*;
import org.apache.poi.hwpf.usermodel.HeaderStories;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

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
        Parser parser = new Parser(paragraphs);
        return parser.parseText();
    }
}
