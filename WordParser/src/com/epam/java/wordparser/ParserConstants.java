package com.epam.java.wordparser;

public interface ParserConstants {
   //Regex constants
   String PACKAGE_OPEN = "\\{\\s*\\[\\s*([rR]{1}.){0,1}\\'[а-яА-Я\\w\\s]*\\'\\]\\s*\\}";
   String PACKAGE_ROOT_OPEN = "\\{\\s*\\[\\s*([rR]{1}.){1}\\'[а-яА-Я\\w\\s]*\\'\\]\\s*\\}";
   String PACKAGE_CLOSE = "\\{\\s*\\[\\s*\\'[а-яА-Я\\w\\s]*\\'\\s*\\/\\s*\\]\\s*\\}";

   String OBJECT_OPEN = "<\\s*(element|diagram|package){1}\\s*[\\(]{0,1}\\w*[\\)]{0,1}\\s*>";
   String OBJECT_CLOSE = "<\\s*(element|diagram|package){1}\\s*[\\(]{0,1}\\w*[\\)]{0,1}\\s*\\/>";

}
