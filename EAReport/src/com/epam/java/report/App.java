package com.epam.java.report;

import com.epam.java.report.eaparser.parser.EAParser;
import com.epam.java.report.wordparser.Reader;
import com.epam.java.report.wordparser.beans.PackageBean;

import java.util.List;

public class App {
    public static void main(String[] args) throws Exception{
        String wordFileName = "D:\\programming\\java\\diplom\\Diplom\\WordParser\\word_docs\\test2.docx";
        String eaFileName = "D:\\test.EAP";
        List<PackageBean> searchParameters= Reader.open(wordFileName);
        new EAParser(eaFileName).search(searchParameters);
    }
}
