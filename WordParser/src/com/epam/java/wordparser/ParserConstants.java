package com.epam.java.wordparser;

public interface ParserConstants {
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
}
