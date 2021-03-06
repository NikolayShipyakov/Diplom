package com.epam.java.wordparser;

import com.epam.java.wordparser.beans.ObjectBean;
import com.epam.java.wordparser.beans.PackageBean;
import com.epam.java.wordparser.beans.ParameterBean;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import java.util.regex.Pattern;

public class Parser {

    public static final Logger logger= Logger.getLogger(Parser.class);

    List<PackageBean> packagesList = new ArrayList<PackageBean>();
    private List<String> text;
    private static final Pattern PACKAGE_OPEN_PATTERN = Pattern.compile(ParserConstants.PACKAGE_OPEN);
    private static final Pattern PACKAGE_ROOT_OPEN_PATTERN = Pattern.compile(ParserConstants.PACKAGE_ROOT_OPEN);
    private static final Pattern PACKAGE_CLOSE_PATTERN = Pattern.compile(ParserConstants.PACKAGE_CLOSE);
    private static final Pattern OBJECT_OPEN_PATTERN = Pattern.compile(ParserConstants.OBJECT_OPEN);
    private static final Pattern OBJECT_ROOT_OPEN_PATTERN = Pattern.compile(ParserConstants.OBJECT_ROOT_OPEN);
    private static final Pattern OBJECT_CLOSE_PATTERN = Pattern.compile(ParserConstants.OBJECT_CLOSE);
    private static final Pattern PARAMETER_PATTERN = Pattern.compile(ParserConstants.PARAMATER);
    private static final Pattern ADDITIONAL_COMMAND_PATTERN = Pattern.compile(ParserConstants.ADDITIONAL_COMMAND);

    public Parser(List<String> text) {
        this.text = text;
    }

    public List<PackageBean> parseText() {
        List<PackageBean> result = new ArrayList<PackageBean>();
        List<PackageParser> packageParsers = new ArrayList<PackageParser>();
        long startPackage = -1;
        long endPackage = -1;
        List<String> packageText = null;
        for (int i = 0; i < text.size(); i++) {
            String currentParagraph = text.get(i);
            if (currentParagraph != null) {
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
        }
        for (PackageParser parser : packageParsers) {
            parser.parsePackage();
            result.add(parser.packageBean);
        }
        logger.debug("Doc parse result:" + result);
        return result;
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
            packageBean.setObjects(getObjects());
        }

        private String parseName(String text) {
            int begin = text.indexOf("'");
            int end = text.indexOf("'", begin + 1);
            return text.substring(begin, end);
        }

        private boolean isRootPackage(String tag) {
            return PACKAGE_ROOT_OPEN_PATTERN.matcher(tag).matches();
        }

        private List<ObjectBean> getObjects() {
            List<ObjectBean> objects = new ArrayList<ObjectBean>();
            List<String> objectText = null;
            List<ParameterBean> parameterList = null;
            ObjectBean currentBean = null;
            for (String paragraph : packageText) {
                if (OBJECT_OPEN_PATTERN.matcher(paragraph).matches()) {
                    objectText = new ArrayList<String>();
                    parameterList = new ArrayList<ParameterBean>();
                    currentBean = new ObjectBean();
                    currentBean.setName(getObjectName(paragraph));

                }
                if (PARAMETER_PATTERN.matcher(paragraph).find()) {
                    String[] words = paragraph.split("\\s+");
                    for (String word : words) {
                        if (PARAMETER_PATTERN.matcher(word).matches()) {
                            try {
                                parameterList.add(parseParameter(word));
                            } catch (Exception e) {
                                logger.error(e);
                            }
                        }
                    }
                }
                if (OBJECT_CLOSE_PATTERN.matcher(paragraph).matches()) {
                    currentBean.setParameters(parameterList);
                    objects.add(currentBean);
                    objectText = null;
                    parameterList = null;
                }
                if (objectText != null) {
                    objectText.add(paragraph);
                }
            }
            return objects;
        }

        private String getObjectName(String openTag) {
            int beginPosition = openTag.indexOf("<");
            int endPosition = openTag.indexOf("(");
            if (endPosition < 0) {
                endPosition = openTag.indexOf(">");
            }
            return openTag.substring(beginPosition + 1, endPosition).trim();
        }

        private ParameterBean parseParameter(String str) {
            ParameterBean result;
            if (ADDITIONAL_COMMAND_PATTERN.matcher(str).matches()) {
                result = new ParameterBean();
                String[] parameters = str.substring(str.indexOf("(") + 1, str.indexOf(")")).split(",");
                String name = str.substring(str.indexOf("{") + 1, str.indexOf("(")).trim();
                result.setName(name);
                result.setAdditionalCommand(true);

                if (parameters.length > 0 && parameters[0] != null && !ParserConstants.EMPTY_STRING.equals(parameters[0])) {
                    result.setParameter1(parameters[0]);
                }
                if (parameters.length > 1 && parameters[1] != null && !ParserConstants.EMPTY_STRING.equals(parameters[1])) {
                    result.setParameter2(parameters[1]);
                }
                if (parameters.length > 2 && parameters[2] != null && !ParserConstants.EMPTY_STRING.equals(parameters[2])) {
                    result.setParameter3(parameters[2]);
                }
                if (parameters.length > 3 && parameters[3] != null && !ParserConstants.EMPTY_STRING.equals(parameters[3])) {
                    result.setParameter4(parameters[3]);
                }
            } else {
                String name = str.substring(str.indexOf("{") + 1, str.indexOf("}"));
                result = new ParameterBean();
                result.setName(name);

            }
            return result;
        }
    }
}
