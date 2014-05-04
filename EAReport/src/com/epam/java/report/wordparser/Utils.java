package com.epam.java.report.wordparser;

import java.util.regex.Pattern;

/**
 * Created by nic on 02.05.2014.
 */
public class Utils {
    //Regex constants
    //Package constants
    String PACKAGE_OPEN = "\\{\\s*\\[\\s*([rR]{1}.){0,1}\\'[а-яА-Я\\w\\s]*\\'\\]\\s*\\}";
    String PACKAGE_ROOT_OPEN = "\\{\\s*\\[\\s*([rR]{1}.){1}\\'[а-яА-Я\\w\\s]*\\'\\]\\s*\\}";
    String PACKAGE_CLOSE = "\\{\\s*\\[\\s*\\'[а-яА-Я\\w\\s]*\\'\\s*\\/\\s*\\]\\s*\\}";
    //Object constants
    String OBJECT_OPEN = "<\\s*([rR]{1}.){0,1}(element|diagram|package){1}\\s*[\\(]{0,1}\\w*[\\)]{0,1}\\s*(if\\s+[а-яА-Я\\w\\s\\'=]*){0,1}>";
    String OBJECT_ROOT_OPEN = "<\\s*([rR]{1}.){1}(element|diagram|package){1}\\s*[\\(]{0,1}\\w*[\\)]{0,1}\\s*(if\\s+[а-яА-Я\\w\\s\\'=]*){0,1}>";
    String OBJECT_CLOSE = "<\\s*(element|diagram|package){1}\\s*[\\(]{0,1}\\w*[\\)]{0,1}\\s*\\/>";
    //Parameter constants
    String PARAMATER = "\\{\\s*[а-яА-Я\\w\\s\\.\\,\\(\\)]*\\s*\\}";
    String ADDITIONAL_COMMAND = "\\{\\s*[а-яА-Я\\w]*\\s*\\([а-яА-Я\\w\\s]*(\\,[а-яА-Я\\w\\s]*)*\\)(.[а-яА-Я\\w]*){0,1}\\s*\\}";

    // Common constants
    String EMPTY_STRING = "";

    // Compile regex
    static final Pattern PACKAGE_OPEN_PATTERN = Pattern.compile(ParserConstants.PACKAGE_OPEN);
    static final Pattern PACKAGE_ROOT_OPEN_PATTERN = Pattern.compile(ParserConstants.PACKAGE_ROOT_OPEN);
    static final Pattern PACKAGE_CLOSE_PATTERN = Pattern.compile(ParserConstants.PACKAGE_CLOSE);
    static final Pattern OBJECT_OPEN_PATTERN = Pattern.compile(ParserConstants.OBJECT_OPEN);
    static final Pattern OBJECT_ROOT_OPEN_PATTERN = Pattern.compile(ParserConstants.OBJECT_ROOT_OPEN);
    static final Pattern OBJECT_CLOSE_PATTERN = Pattern.compile(ParserConstants.OBJECT_CLOSE);
    static final Pattern PARAMETER_PATTERN = Pattern.compile(ParserConstants.PARAMATER);
    static final Pattern ADDITIONAL_COMMAND_PATTERN = Pattern.compile(ParserConstants.ADDITIONAL_COMMAND);

    // Tmpl
    static final String OPEN_PACKAGE_TMPL = "pkgOpn_";
    static final String CLOSE_PACKAGE_TMPL = "pkgCls_";
}
