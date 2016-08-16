package com.annimon.jmr.views;

import com.annimon.jmr.models.Method;
import javafx.scene.control.ListCell;
import javafx.scene.input.DataFormat;

public final class MethodCell extends ListCell<Method> {

    public MethodCell() {
        init();
    }

    private void init() {
        ListCellUtils.setArrangable(this, new DataFormat(Method.class.getName()));
    }

    @Override
    protected void updateItem(Method item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null);
        } else {
            setText(item.getDeclaration());
        }
    }
}
