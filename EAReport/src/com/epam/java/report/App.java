package com.epam.java.report;

import com.epam.java.report.eaparser.parser.EAParser;
import com.epam.java.report.wordparser.Reader;
import com.epam.java.report.wordparser.Replacer;
import com.epam.java.report.wordparser.beans.PackageBean;

import java.util.List;

public class App {
    public static void main(String[] args) throws Exception{
        String path = System.getProperty("java.library.path");
        System.out.println(path);
        String wordFileName = "D:\\study\\Diplom\\WordParser\\word_docs\\test_by_test_ear.docx";
        String eaFileName = "D:\\test.EAP";
        List<PackageBean> searchParameters= Reader.open(wordFileName);
        new EAParser(eaFileName).search(searchParameters);
        new Replacer().createResultDoc(searchParameters, wordFileName);
    }
}
