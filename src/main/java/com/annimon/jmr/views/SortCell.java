package com.annimon.jmr.views;

import com.annimon.jmr.models.Sort;
import java.util.function.Predicate;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.util.StringConverter;

public final class SortCell extends CheckBoxListCell<Sort> {

    private static final DataFormat INDEX_DATAFORMAT = new DataFormat(Sort.class.getName());

    public SortCell() {
        init();
    }

    private void init() {
        final Predicate<DragEvent> hasItemInDragBoard = event ->
                event.getGestureSource() != this && event.getDragboard().hasContent(INDEX_DATAFORMAT);
        
        setOnDragDetected(event -> {
            if (getItem() == null) return;

            final Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
            final ClipboardContent content = new ClipboardContent();
            final int index = getListView().getItems().indexOf(getItem());
            content.put(INDEX_DATAFORMAT, index);
            dragboard.setContent(content);

            event.consume();
        });
        setOnDragOver(event -> {
            if (hasItemInDragBoard.test(event)) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });
        setOnDragEntered(event -> {
            if (hasItemInDragBoard.test(event)) {
                setOpacity(0.3);
            }
        });
        setOnDragExited(event -> {
            if (hasItemInDragBoard.test(event)) {
                setOpacity(1);
            }
        });
        setOnDragDropped(event -> {
            if (getItem() == null) return;

            final Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasContent(INDEX_DATAFORMAT)) {
                final ObservableList<Sort> items = getListView().getItems();
                final int fromIndex = (int) db.getContent(INDEX_DATAFORMAT);
                int toIndex = items.indexOf(getItem());
                final Sort sort = items.remove(fromIndex);
                items.add(toIndex, sort);
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });
        setOnDragDone(DragEvent::consume);

        setConverter(new StringConverter<Sort>() {
            @Override
            public String toString(Sort sort) {
                return sort.getName();
            }

            @Override
            public Sort fromString(String string) {
                return null;
            }
        });

        setSelectedStateCallback(Sort::enabledProperty);
    }
}
