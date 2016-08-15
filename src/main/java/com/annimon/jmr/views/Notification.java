package com.annimon.jmr.views;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public final class Notification {

    public static void error(String message) {
        final Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void success(String message) {
        final Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
