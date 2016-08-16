package com.annimon.jmr.views;

import com.annimon.jmr.models.Method;
import javafx.scene.control.ListCell;
import javafx.scene.input.DataFormat;

public final class MethodCell extends ListCell<Method> {

    private static final DataFormat DATA_FORMAT = new DataFormat(Method.class.getName());
    
    public MethodCell() {
        init();
    }

    private void init() {
        ListCellUtils.setArrangable(this, DATA_FORMAT);
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
