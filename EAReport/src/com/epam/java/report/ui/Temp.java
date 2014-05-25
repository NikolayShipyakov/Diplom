package com.epam.java.report.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class Temp extends Application {
    public static void main(String[] args) {
        Application.launch(Temp.class, args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("fxml/Sample.fxml"));
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch(Exception e){
            System.out.print(e);
        }
    }
}
