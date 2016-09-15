package com.annimon.jmr;

import com.annimon.jmr.controllers.MainController;
import java.io.IOException;
import java.util.stream.Stream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setTitle("JMethodRearranger");
        primaryStage.setScene(scene);
        MainController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);
        primaryStage.show();
    }
}
