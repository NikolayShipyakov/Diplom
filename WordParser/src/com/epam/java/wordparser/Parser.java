package com.epam.java.wordparser;

import com.epam.java.wordparser.beans.PackageBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Parser {
    List<PackageBean> packagesList = new ArrayList<PackageBean>();
    private Map<Long, String> text;
    private static final Pattern PACKAGE_OPEN_PATTERN = Pattern.compile(ParserConstants.PACKAGE_OPEN);
    private static final Pattern PACKAGE_ROOT_OPEN_PATTERN = Pattern.compile(ParserConstants.PACKAGE_ROOT_OPEN);
    private static final Pattern PACKAGE_CLOSE_PATTERN = Pattern.compile(ParserConstants.PACKAGE_CLOSE);
    private static final Pattern OBJECT_OPEN_PATTERN = Pattern.compile(ParserConstants.OBJECT_OPEN);
    private static final Pattern OBJECT_CLOSE_PATTERN = Pattern.compile(ParserConstants.OBJECT_CLOSE);

    public Parser(Map<Long, String> text) {
        this.text = text;
    }

    public List<PackageBean> parseText() {
        List<PackageParser> packageParsers = new ArrayList<PackageParser>();
        long startPackage = -1;
        long endPackage = -1;
        List<String> packageText = null;
        for (long i = 0; i < text.size(); i++) {
            String currentParagraph = text.get(i);
            if (PACKAGE_OPEN_PATTERN.matcher(currentParagraph).matches()) {
                startPackage = i;
                packageText = new ArrayList<String>();
            }
            if (packageText != null) {
                packageText.add(currentParagraph);
            }
            if (PACKAGE_CLOSE_PATTERN.matcher(currentParagraph).matches()) {
                endPackage = i;
                if (packageText != null) {
                    PackageParser packageParser = new PackageParser(packageText);
                    packageParsers.add(packageParser);
                }
                startPackage = -1;
                endPackage = -1;
                packageText = null;
            }
        }
        for (PackageParser parser : packageParsers) {
            parser.parsePackage();
        }
        return null;
    }

    private class PackageParser {
        private List<String> packageText;
        private PackageBean packageBean;

        public PackageParser(List<String> packageText) {
            this.packageText = packageText;
        }

        public void parsePackage() {
            packageBean = new PackageBean();
            packageBean.setName(parseName(packageText.get(0)));
            packageBean.setRoot(isRootPackage(packageText.get(0)));
        }

        private String parseName(String text) {
            int begin = text.indexOf("'");
            int end = text.indexOf("'", begin + 1);
            return text.substring(begin, end);
        }

        private boolean isRootPackage(String tag){
            return PACKAGE_ROOT_OPEN_PATTERN.matcher(tag).matches();
        }
    }
}
