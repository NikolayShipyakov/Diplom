package com.epam.java.report.ui;

import java.io.File;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import com.epam.java.report.eaparser.parser.EAParser;
import com.epam.java.report.wordparser.Reader;
import com.epam.java.report.wordparser.Replacer;
import com.epam.java.report.wordparser.beans.PackageBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import javax.swing.*;

public class Main implements Initializable {
    @FXML
    private BorderPane sample;
    @FXML
    private TextField templatePath;
    @FXML
    private TextField eaPath;
    @FXML
    private ProgressIndicator progressIndicator;
    final FileChooser fileChooser = new FileChooser();

    @FXML
    private void handleTemplateAction(ActionEvent event) {
        final File importFile = fileChooser.showOpenDialog(null);
        String s = Calendar.getInstance().getTime().toString();
        templatePath.setText(importFile.getPath());
    }

    @FXML
    private void handleModelAction(ActionEvent event) {
        final File importFile = fileChooser.showOpenDialog(null);
        eaPath.setText(importFile.getPath());
    }

    @FXML
    private void handleExportAction(ActionEvent event) throws Exception{
        if(!(eaPath.getText().trim().equals("")) && !(templatePath.getText().trim().equals(""))){
            progressIndicator.setVisible(true);
            (new Thread(){
                @Override
                public void run() {
                    try {
                        List<PackageBean> searchParameters = Reader.open(templatePath.getText());
                        new EAParser(eaPath.getText()).search(searchParameters);
                        new Replacer().createResultDoc(searchParameters, templatePath.getText());
                    } catch (Exception e){}
                    progressIndicator.setVisible(false);
                }
            }).start();

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}