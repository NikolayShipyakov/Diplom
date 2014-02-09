package com.epam.java.report;

import com.epam.java.report.wordparser.Reader;

public class App {
    public static void main(String[] args) throws Exception{
        String fileName = "D:\\programming\\java\\diplom\\Diplom\\WordParser\\word_docs\\test2.docx";
        Reader.open(fileName);
    }
}
