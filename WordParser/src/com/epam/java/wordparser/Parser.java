package com.epam.java.wordparser;

import com.epam.java.wordparser.beans.PackageBean;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Parser {
      private Map<Long, String> text;
      private static final Pattern PACKAGE_OPEN_PATTERN = Pattern.compile(ParserConstants.PACKAGE_OPEN);

    public Parser(Map<Long, String> text){
          this.text = text;
      }

      public List<PackageBean> parseText(){
          for(long i = 0; i < text.size(); i++){
              String currentParagraph = text.get(i);
              PACKAGE_OPEN_PATTERN.matcher(currentParagraph).matches();
          }
          return null;
      }
}
