package com.epam.java.report.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {

    private Properties properties = new Properties();

    public PropertiesReader(String path) throws FileNotFoundException, IOException{
        properties.load(new FileInputStream(path));
    }
}
