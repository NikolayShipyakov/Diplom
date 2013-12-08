package com.epam.java.wordparser;

public interface ParserConstants {
   //Regex constants
   String PACKAGE_OPEN = "\\{\\s*\\[\\s*([rR]{1}.){0,1}\\'[а-яА-Я\\w\\s]*\\'\\]\\s*\\}";
   String PACKAGE_CLOSE = "\\{\\s*\\[\\s*\\'[а-яА-Я\\w\\s]*\\'\\s*\\/\\s*\\]\\s*\\}";

}
