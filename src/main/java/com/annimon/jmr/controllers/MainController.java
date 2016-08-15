package com.annimon.jmr.controllers;

import com.annimon.jmr.MethodRearranger;
import com.annimon.jmr.models.Method;
import com.annimon.jmr.views.MethodCell;
import com.github.javaparser.ast.CompilationUnit;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.binding.Bindings;
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
        panelMethods.disableProperty().bind(Bindings.isEmpty(lvMethods.getItems()));
        lvMethods.setCellFactory(param -> new MethodCell());

        taSource.setText("class Main {\n\n    public void method1() {}\n\n"
                + "    public void test() {}\n\n"
                + "    public void method2() {\n        // comment\n    }\n }");
    }

    @FXML
    private void handleParse(ActionEvent event) {
        rearranger = MethodRearranger.from(taSource.getText());
        lvMethods.getItems().addAll(rearranger.methods()
                .collect(Collectors.toList())
        );
    }

    @FXML
    private void handleSortAsc(ActionEvent event) {
        final Comparator<Method> byName = Comparator.comparing(m -> m.getMethod().getName());
        final Comparator<Method> byParametersCount = Comparator.comparing(m -> m.getMethod().getParameters().size());

        final List<Method> sortedMethods = lvMethods.getItems().stream()
                .sorted(byName.thenComparing(byParametersCount))
                .collect(Collectors.toList());
        lvMethods.getItems().clear();
        lvMethods.getItems().addAll(sortedMethods);
    }

    @FXML
    private void handleSave(ActionEvent event) {
        final CompilationUnit cu = rearranger.modifyMethods(lvMethods.getItems());
        System.out.println(cu);
    }
}
