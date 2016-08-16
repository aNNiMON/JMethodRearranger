package com.annimon.jmr.views;

import java.util.function.Predicate;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public final class ListCellUtils {

    public static <T> void setArrangable(ListCell<T> cell, DataFormat dataFormat) {
        final Predicate<DragEvent> hasItemInDragBoard = event ->
                event.getGestureSource() != cell && event.getDragboard().hasContent(dataFormat);

        cell.setOnDragDetected(event -> {
            if (cell.getItem() == null) return;

            final Dragboard dragboard = cell.startDragAndDrop(TransferMode.MOVE);
            final ClipboardContent content = new ClipboardContent();
            final int index = cell.getListView().getItems().indexOf(cell.getItem());
            content.put(dataFormat, index);
            dragboard.setContent(content);

            event.consume();
        });
        cell.setOnDragOver(event -> {
            if (hasItemInDragBoard.test(event)) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });
        cell.setOnDragEntered(event -> {
            if (hasItemInDragBoard.test(event)) {
                cell.setOpacity(0.3);
            }
        });
        cell.setOnDragExited(event -> {
            if (hasItemInDragBoard.test(event)) {
                cell.setOpacity(1);
            }
        });
        cell.setOnDragDropped(event -> {
            if (cell.getItem() == null) return;

            final Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasContent(dataFormat)) {
                final ObservableList<T> items = cell.getListView().getItems();
                final int fromIndex = (int) db.getContent(dataFormat);
                int toIndex = items.indexOf(cell.getItem());
                final T obj = items.remove(fromIndex);
                items.add(toIndex, obj);
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });
        cell.setOnDragDone(DragEvent::consume);
    }
}
