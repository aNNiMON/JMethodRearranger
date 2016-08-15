package com.annimon.jmr.controllers;

import com.annimon.jmr.MethodRearranger;
import com.annimon.jmr.models.Method;
import static com.annimon.jmr.models.MethodComparators.*;
import com.annimon.jmr.views.MethodCell;
import com.github.javaparser.ast.CompilationUnit;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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

        try (InputStream is = getClass().getResourceAsStream("/example.txt")) {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            final byte[] buffer = new byte[1024];
            int readed;
            while ( (readed = is.read(buffer, 0, buffer.length)) != -1 ) {
                baos.write(buffer, 0, readed);
            }
            taSource.setText(baos.toString("UTF-8"));
        } catch (IOException ex) {
            taSource.setText("class Main {\n\n    public void method1() {}\n\n"
                    + "    public void test() {}\n\n"
                    + "    public void method2() {\n        // comment\n    }\n }");
        }
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
        final List<Method> sortedMethods = lvMethods.getItems().stream()
                .sorted(byName().thenComparing(byParametersCount()))
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
