package com.annimon.jmr.controllers;

import com.annimon.jmr.MethodRearranger;
import com.annimon.jmr.models.Method;
import static com.annimon.jmr.models.MethodComparators.*;
import com.annimon.jmr.models.Sort;
import com.annimon.jmr.views.MethodCell;
import com.annimon.jmr.views.Notification;
import com.annimon.jmr.views.SortCell;
import com.annimon.jmr.visitors.ClassNameVisitor;
import com.github.javaparser.ast.CompilationUnit;
import java.io.File;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainController implements Initializable {

    @FXML
    private SplitPane root;
    
    @FXML
    private BorderPane panelMethods, panelSorts;

    @FXML
    private TextArea taSource;

    @FXML
    private Button btnParse, btnSortAsc, btnSave;

    @FXML
    private ListView<Method> lvMethods;

    @FXML
    private ListView<Sort> lvSorts;

    private MethodRearranger rearranger;

    private Stage primaryStage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        panelMethods.disableProperty().bind(Bindings.isEmpty(lvMethods.getItems()));
        panelSorts.disableProperty().bind(panelMethods.disableProperty());
        lvMethods.setCellFactory(param -> new MethodCell());
        lvSorts.setCellFactory(param -> new SortCell());
        lvSorts.getItems().addAll(
                new Sort("public access", byAccessPublic(), true),
                new Sort("static", byModifierStatic(), true),
                new Sort("final", byModifierFinal()),
                new Sort("name", byName(), true),
                new Sort("parameters count", byParametersCount()),
                new Sort("throws count", byThrowsCount()),
                new Sort("protected access", byAccessProtected()),
                new Sort("default access", byAccessDefault()),
                new Sort("private access", byAccessPrivate()),
                new Sort("abstract", byModifierAbstract()),
                new Sort("native", byModifierNative()),
                new Sort("synchronized", byModifierSynchronized()),
                new Sort("name reversed", byNameReversed()),
                new Sort("parameters count reversed", byParametersCountReversed()),
                new Sort("throws count reversed", byThrowsCountReversed())
        );

        final String content = IOUtil.resourceToString("/example.txt")
                .filter(p -> !p.isEmpty())
                .orElse("class Main {\n\n    public void method1() {}\n\n"
                    + "    public void test() {}\n\n"
                    + "    public void method2() {\n        // comment\n    }\n }");
        taSource.setText(content);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void handleParse(ActionEvent event) {
        rearranger = MethodRearranger.from(taSource.getText());
        lvMethods.getItems().clear();
        lvMethods.getItems().addAll(rearranger.methods()
                .collect(Collectors.toList())
        );
    }

    @FXML
    private void handleSortAsc(ActionEvent event) {
        for (Sort item : lvSorts.getItems()) {
            item.setEnabled(item.getComparator() == byName());
        }
        sortMethods();
    }

    @FXML
    private void handleSort(ActionEvent event) {
        sortMethods();
    }

    private void sortMethods() {
        final List<Method> sortedMethods = lvMethods.getItems().stream()
                .sorted(build(lvSorts.getItems()))
                .collect(Collectors.toList());
        lvMethods.getItems().clear();
        lvMethods.getItems().addAll(sortedMethods);
    }

    @FXML
    private void handleShow(ActionEvent event) {
        final CompilationUnit cu = rearranger.modifyMethods(lvMethods.getItems());
        taSource.setText(cu.toString());
        taSource.selectAll();
        taSource.requestFocus();
    }

    @FXML
    private void handleSave(ActionEvent event) {
        final CompilationUnit cu = rearranger.modifyMethods(lvMethods.getItems());
        String className = ClassNameVisitor.getClassName(cu);
        if (className == null || className.isEmpty()) {
            className = "Main";
        }

        final FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        chooser.setInitialFileName(className + ".java");
        chooser.setTitle("Save source code as");
        final File file = chooser.showSaveDialog(primaryStage);
        if (file == null) return;

        IOUtil.stringToFile(file, cu.toString(),
                f -> Notification.success("Source code writed to " + f.getAbsolutePath()),
                f -> Notification.error("Unable to write file: " + f.getAbsolutePath()));
    }
}
