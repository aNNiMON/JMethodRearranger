package com.annimon.jmr.controllers;

import com.annimon.jmr.MethodRearranger;
import com.annimon.jmr.models.Method;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

public class MainController implements Initializable {

    @FXML
    private SplitPane root;
    
    @FXML
    private BorderPane panelMethods;

    @FXML
    private TextArea taSource;

    @FXML
    private Button btnParse, btnSortAsc, btnSave;

    @FXML
    private ListView<Method> lvMethods;

    private MethodRearranger rearranger;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @FXML
    private void handleParse(ActionEvent event) {
        
    }

    @FXML
    private void handleSortAsc(ActionEvent event) {
        
    }

    @FXML
    private void handleSave(ActionEvent event) {
        
    }
}
